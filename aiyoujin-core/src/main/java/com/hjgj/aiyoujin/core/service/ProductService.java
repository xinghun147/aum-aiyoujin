package com.hjgj.aiyoujin.core.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.common.exception.BusinessException;
import com.hjgj.aiyoujin.core.common.exception.EnumError;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.ProductCustomMapper;
import com.hjgj.aiyoujin.core.dao.ProductMapper;
import com.hjgj.aiyoujin.core.model.Product;
import com.hjgj.aiyoujin.core.model.ProductExample;
import com.hjgj.aiyoujin.core.model.ProductExample.Criteria;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.model.vo.ProductVo;

@Service
public class ProductService extends BaseService {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductCustomMapper productCustomMapper;
	
	
	@Transactional
	public int insertOrUpdate(Product product){ 
		//id不存在新增
		if(StringUtils.isBlank(product.getId())){
			product.setId(UUIDGenerator.generate());
			product.setUpdateTime(new Date());
			product.setCreateTime(new Date());
			product.setDeleted(Constants.DelFlag.NO.ordinal());
			product.setStatus(Constants.ProdStatus.draft.ordinal());
			return productMapper.insertSelective(product);
		}
		product.setUpdateTime(new Date());
		return productMapper.updateByPrimaryKeySelective(product);
	}
	
	public Product findProduct(String id){
		return productMapper.selectByPrimaryKey(id);
	}
	

	public int updateProductStatus(String id,int status, String operation){
		Product product = productMapper.selectByPrimaryKey(id);
		Date date = new Date();
		switch(status){
			case 1:   //上架
				product.setPublishTime(date);
				break;
			case 2:  //开售
				product.setSellBeginTime(date);
				break;
			case 4: //下架
				product.setRemoveTime(date);
				break;
		}
		product.setUpdateTime(date);
		product.setUpdateBy(operation);
		product.setStatus(status);
		return productMapper.updateByPrimaryKeyWithBLOBs(product);
	}
	
	public List<Product> queryAll(){
		ProductExample example = new ProductExample();
		return productMapper.selectByExampleWithBLOBs(example); 
	}
	
	public Page<Product> queryPageProduct(Product product,Integer pageNum, Integer pageSize){
		Page<Product> page = new Page<Product>(pageNum,pageSize,true);
		ProductExample example = new ProductExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(product.getName())){
			criteria.andNameEqualTo(product.getName());
		}
		if(StringUtils.isNotBlank(product.getCode())){
			criteria.andCodeEqualTo(product.getCode());
		}
		if(product.getStatus() != null){
			criteria.andStatusEqualTo(product.getStatus());
		}
		example.setOrderByClause("create_time desc");
		int total = productMapper.countByExample(example);
		if(total > 0){
			example.setLimitOffset(page.getStartRow());
			example.setLimitRows(pageSize);
			List<Product>  list = productMapper.selectByExample(example);
			page.setTotal(total);
			page.setList(list);
		}
		return page;
	}
	
	public Page<ProductVo> queryPageGoods(Product product,Integer pageNum, Integer pageSize) {
		Page<ProductVo> page = new Page<ProductVo>(pageNum,pageSize,true);
		Map<String,Object> map = new HashMap<String,Object>();
		int total = productCustomMapper.queryPageGoodsCount(map);
		if(total > 0){
			RowBounds rowBounds = new RowBounds(page.getStartRow(),pageSize);
			List<ProductVo> list = productCustomMapper.queryPageGoods(map,rowBounds);
			page.setTotal(total);
			page.setList(list);
		}
		return page;
	}
	
	public ProductVo queryGoodsDetail(String id) {
		if(StringUtils.isBlank(id)){
			logger.error("查询产品详细信息id为null");
			return null;
		}
		return productCustomMapper.queryGoodsDetail(id);
	}
	/**
	 * 
	 * @Title:        updateQuantity 
	 * @Description:  更新库存  负数为扣减 正数为释放增加
	 * @param:        @param id
	 * @param:        @param quantity    
	 * @return:       void    
	 * @throws 			
	 * @author        ailiming@gold32.com
	 * @Date          2018年1月13日 下午2:35:22
	 */
	public void updateQuantity(String id,Integer quantity) throws BusinessException{
		if(StringUtils.isBlank(id)|| quantity == null){
			logger.error("更新库存商品id为null");
			return ;
		}
		Product product = productMapper.getAndLock(id);
		if(quantity < 0 && product.getQuantity() < Math.abs(quantity)){
			throw new BusinessException(EnumError.INSUFFICIENT_STOCK_ERROR);
		}
		product.setQuantity(product.getQuantity()+quantity);
		productMapper.updateByPrimaryKeySelective(product);
	}
}
