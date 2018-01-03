package com.hjgj.aiyoujin.dao;

import com.hjgj.aiyoujin.model.UserAddress;
import com.hjgj.aiyoujin.model.UserAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAddressMapper {
    int countByExample(UserAddressExample example);

    int deleteByExample(UserAddressExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectOneByExample(UserAddressExample example);

    List<UserAddress> selectByExample(UserAddressExample example);

    UserAddress selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserAddress record, @Param("example") UserAddressExample example);

    int updateByExample(@Param("record") UserAddress record, @Param("example") UserAddressExample example);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);
}