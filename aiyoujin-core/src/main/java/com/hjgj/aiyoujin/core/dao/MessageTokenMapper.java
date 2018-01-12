package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.MessageToken;
import com.hjgj.aiyoujin.core.model.MessageTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageTokenMapper {
    int countByExample(MessageTokenExample example);

    int deleteByExample(MessageTokenExample example);

    int deleteByPrimaryKey(String id);

    int insert(MessageToken record);

    int insertSelective(MessageToken record);

    MessageToken selectOneByExample(MessageTokenExample example);

    List<MessageToken> selectByExample(MessageTokenExample example);

    MessageToken selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MessageToken record, @Param("example") MessageTokenExample example);

    int updateByExample(@Param("record") MessageToken record, @Param("example") MessageTokenExample example);

    int updateByPrimaryKeySelective(MessageToken record);

    int updateByPrimaryKey(MessageToken record);
}