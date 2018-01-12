package com.hjgj.aiyoujin.core.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.UserMapper;
import com.hjgj.aiyoujin.core.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	
	public User insertUser(User user) throws Exception{
		user.setCreateTime(new Date());
		user.setDeleted(Constants.DelFlag.NO.ordinal());
		user.setId(UUIDGenerator.generate());
		user.setUpdateTime(new Date());
		userMapper.insert(user);
		return user;
	}

}
