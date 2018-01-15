package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.UserMapper;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.UserExample;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    @Transactional
    public User insertUser(User user) throws Exception {
	    UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andOpenIdEqualTo(user.getOpenId());
    	User currUser = userMapper.selectOneByExample(example);
    	if(currUser != null){
    		currUser.setUpdateTime(new Date());
    		if(StringUtils.isNotBlank(user.getAvatar())){
    			currUser.setAvatar(user.getAvatar());
    		}
    		if(StringUtils.isNotBlank(user.getNickname())){
    			currUser.setNickname(user.getNickname());
    		}
    	    userMapper.updateByPrimaryKeySelective(currUser);
    	    return currUser;
    	}else{
    		user.setId(UUIDGenerator.generate());
    		user.setCreateTime(new Date());
    		user.setDeleted(Constants.DelFlag.NO.ordinal());
    		user.setUpdateTime(new Date());
    		userMapper.insert(user);
    	}
        return user;
    }

    public User getUserByOpenId(String openId) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andOpenIdEqualTo(openId).andDeletedEqualTo(0); // 0 正常

        List<User> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }                                        
        return null;
    }
}
