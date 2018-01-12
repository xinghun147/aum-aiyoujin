package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.ProductMessage;
import com.hjgj.aiyoujin.core.model.ProductMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductMessageMapper {
    int countByExample(ProductMessageExample example);

    int deleteByExample(ProductMessageExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductMessage record);

    int insertSelective(ProductMessage record);

    ProductMessage selectOneByExample(ProductMessageExample example);

    List<ProductMessage> selectByExample(ProductMessageExample example);

    ProductMessage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductMessage record, @Param("example") ProductMessageExample example);

    int updateByExample(@Param("record") ProductMessage record, @Param("example") ProductMessageExample example);

    int updateByPrimaryKeySelective(ProductMessage record);

    int updateByPrimaryKey(ProductMessage record);
}