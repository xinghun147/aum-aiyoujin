package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.UserExample;
import com.hjgj.aiyoujin.core.model.vo.UserVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectOneByExample(UserExample example);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int countUserVo(User user, RowBounds rowBounds);

    List<UserVO> selectUserVo(User user, RowBounds rowBounds);
}