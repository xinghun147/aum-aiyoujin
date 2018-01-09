package com.hjgj.aiyoujin.core.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.hjgj.aiyoujin.core.model.vo.ProductVo;



public interface ProductCustomMapper {
	
	public List<ProductVo> queryPageGoods(Map<String,Object> map,RowBounds rowBounds);
	
	public Integer queryPageGoodsCount(Map<String,Object> map);
	
	public ProductVo queryGoodsDetail(String id);
	
}