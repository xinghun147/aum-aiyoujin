package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.ProductMessageMapper;
import com.hjgj.aiyoujin.core.model.ProductMessage;
import com.hjgj.aiyoujin.core.model.ProductMessageExample;
import com.hjgj.aiyoujin.core.model.ProductMessageExample.Criteria;
import com.hjgj.aiyoujin.core.model.vo.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ProductMessageService extends BaseService {

    @Autowired
    private ProductMessageMapper productMessageMapper;


    public int insert(ProductMessage productMessage) {
        productMessage.setId(UUIDGenerator.generate());
        productMessage.setCreateTime(new Date());
        return productMessageMapper.insertSelective(productMessage);
    }


    public List<ProductMessage> queryProductMessage() {
        ProductMessageExample example = new ProductMessageExample();
        return productMessageMapper.selectByExample(example);
    }

    public Page<ProductMessage> queryPageProductMessage(ProductMessage productMessage, Integer pageNum, Integer pageSize) {
        Page<ProductMessage> page = new Page<ProductMessage>(pageNum, pageSize, true);
        ProductMessageExample example = new ProductMessageExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productMessage.getTitle())) {
            criteria.andTitleEqualTo(productMessage.getTitle());
        }
        if (productMessage.getDeleted() != null) {
            criteria.andDeletedEqualTo(productMessage.getDeleted());
        }
        example.setOrderByClause("create_time desc");
        int total = productMessageMapper.countByExample(example);
        if (total > 0) {
            example.setLimitOffset(page.getStartRow());
            example.setLimitRows(pageSize);
            List<ProductMessage> list = productMessageMapper.selectByExample(example);
            page.setTotal(total);
            page.setList(list);
        }
        return page;
    }


    public int updateAddress(ProductMessage productMessage) {
        productMessage.setUpdateTime(new Date());
        return productMessageMapper.updateByPrimaryKeySelective(productMessage);
    }


    public int updateDeleted(String id, Integer deleted) {
        ProductMessageExample example = new ProductMessageExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        ProductMessage productMessage = new ProductMessage();
        productMessage.setDeleted(deleted);
        return productMessageMapper.updateByExampleSelective(productMessage, example);
    }

    public ProductMessage findProduct(String id) {
        return productMessageMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insertOrUpdate(ProductMessage productMessage) {
        //id不存在新增
        if (StringUtils.isBlank(productMessage.getId())) {
            productMessage.setId(UUIDGenerator.generate());
            productMessage.setUpdateTime(new Date());
            productMessage.setCreateTime(new Date());
            productMessage.setDeleted(Constants.DelFlag.NO.ordinal());
            return productMessageMapper.insertSelective(productMessage);
        }
        productMessage.setUpdateTime(new Date());
        return productMessageMapper.updateByPrimaryKeySelective(productMessage);
    }
}
