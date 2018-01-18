package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.ContentTemplate;
import com.hjgj.aiyoujin.core.model.ContentTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentTemplateMapper {
    int countByExample(ContentTemplateExample example);

    int deleteByExample(ContentTemplateExample example);

    int deleteByPrimaryKey(String id);

    int insert(ContentTemplate record);

    int insertSelective(ContentTemplate record);

    ContentTemplate selectOneByExample(ContentTemplateExample example);

    List<ContentTemplate> selectByExample(ContentTemplateExample example);

    ContentTemplate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ContentTemplate record, @Param("example") ContentTemplateExample example);

    int updateByExample(@Param("record") ContentTemplate record, @Param("example") ContentTemplateExample example);

    int updateByPrimaryKeySelective(ContentTemplate record);

    int updateByPrimaryKey(ContentTemplate record);
}