package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.UserAddress;
import com.hjgj.aiyoujin.core.model.UserAddressExample;
import com.hjgj.aiyoujin.core.model.vo.UserAddressVo;
import com.hjgj.aiyoujin.core.vo.AddressVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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

    int countAddressListVO(AddressVo addressVo, RowBounds rowBounds);

    List<UserAddressVo> selectAddressListVO(AddressVo addressVo, RowBounds rowBounds);
}