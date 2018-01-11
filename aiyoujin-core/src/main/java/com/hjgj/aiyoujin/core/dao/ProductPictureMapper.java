package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.ProductPicture;
import com.hjgj.aiyoujin.core.model.ProductPictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPictureMapper {
    int countByExample(ProductPictureExample example);

    int deleteByExample(ProductPictureExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductPicture record);

    int insertSelective(ProductPicture record);

    ProductPicture selectOneByExample(ProductPictureExample example);

    List<ProductPicture> selectByExample(ProductPictureExample example);

    ProductPicture selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductPicture record, @Param("example") ProductPictureExample example);

    int updateByExample(@Param("record") ProductPicture record, @Param("example") ProductPictureExample example);

    int updateByPrimaryKeySelective(ProductPicture record);

    int updateByPrimaryKey(ProductPicture record);
}