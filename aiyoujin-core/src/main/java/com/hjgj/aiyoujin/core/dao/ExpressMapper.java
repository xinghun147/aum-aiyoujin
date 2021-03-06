package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.Express;
import com.hjgj.aiyoujin.core.model.ExpressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExpressMapper {
    int countByExample(ExpressExample example);

    int deleteByExample(ExpressExample example);

    int insert(Express record);

    int insertSelective(Express record);

    Express selectOneByExample(ExpressExample example);

    List<Express> selectByExample(ExpressExample example);

    int updateByExampleSelective(@Param("record") Express record, @Param("example") ExpressExample example);

    int updateByExample(@Param("record") Express record, @Param("example") ExpressExample example);

	List<Express> findExpressName();
}