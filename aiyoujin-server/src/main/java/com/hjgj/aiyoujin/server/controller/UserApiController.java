package com.hjgj.aiyoujin.server.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.service.UserService;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * Created by Lin on 2017/9/5.
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController{
	
	protected final Logger logger = LoggerFactory.getLogger(UserApiController.class);


    @Autowired
    private UserService userService;
    
    
    @ApiOperation(value = "添加用户")
	@RequestMapping(value = "addUser", method = RequestMethod.GET)
	public ResultModel queryGoodsDetail(@ApiParam(value = "微信openId", required = true)  @RequestParam String openId,
										@ApiParam(value = "昵称") @RequestParam(required = false) String nickname,
										@ApiParam(value = "头像地址") @RequestParam(required = false) String avatar) {
    	Assert.notNull(openId, "微信openId can not be empty");
    	if(StringUtils.isBlank(openId)){
    		return ResultModel.error(ResultStatus.USER_NOT_OPENID);
    	}
    	try {
			User user = new User();
			user.setAvatar(avatar);
			user.setOpenId(openId);
			user.setNickname(nickname);
			user = userService.insertUser(user);
			return ResultModel.ok(user);
		} catch (Exception e) {
			logger.error("添加用户接口异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
    
}
