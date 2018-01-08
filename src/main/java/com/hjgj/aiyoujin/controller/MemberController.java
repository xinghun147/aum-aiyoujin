package com.hjgj.aiyoujin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjgj.aiyoujin.common.Constants;
import com.hjgj.aiyoujin.common.ResultStatus;
import com.hjgj.aiyoujin.common.utils.FormatCheckUtils;
import com.hjgj.aiyoujin.model.ResultModel;
import com.hjgj.aiyoujin.model.UserAddress;
import com.hjgj.aiyoujin.service.AddressService;
import com.hjgj.aiyoujin.vo.AddressVo;
@RestController
@RequestMapping("/{version}/member/")
public class MemberController extends BaseController {

	@Autowired
	private AddressService addressService;

	

	//
	@RequestMapping(value = "addAddress", method = RequestMethod.POST)
	public ResultModel addAddress(String userId, 
			@RequestParam String contact,
			 @RequestParam String telephone,
			 @RequestParam String address) {
		Assert.notNull(contact, "联系人 can not be empty");
		Assert.notNull(telephone, "联系电话 can not be empty");
		Assert.notNull(address, "详细地址 can not be empty");
		if(!FormatCheckUtils.isPhoneLegal(telephone)){//手机号格式校验
			return ResultModel.error(ResultStatus.USER_PHONE_ERROR);
		}
		try {
			AddressVo addressVo = new AddressVo();
			addressVo.setContact(contact);
			addressVo.setTelephone(telephone);
			addressVo.setAddress(address);
			addressVo.setUserId(userId);
			UserAddress userAddress = new UserAddress();
			userAddress.setAddress(addressVo.getAddress());
			userAddress.setAreaId(addressVo.getAreaId());
			userAddress.setContact(addressVo.getContact());
			userAddress.setIsDefault(addressVo.getIsDefault());
			userAddress.setPostCode(addressVo.getPostCode());
			userAddress.setTelephone(addressVo.getTelephone());
			userAddress.setUserId(addressVo.getUserId());
			userAddress.setDeleted(Constants.DelFlag.NO.ordinal());
			if(addressVo.getIsDefault() != null && addressVo.getIsDefault() == Constants.DefaultFlag.YES.ordinal()){
				AddressVo tempAddress = new AddressVo();
				tempAddress.setUserId(addressVo.getUserId());
				tempAddress.setIsDefault(Constants.DefaultFlag.YES.ordinal());
				List<UserAddress> list = addressService.queryAddress(tempAddress);
				if(list != null && list.size() > 0){
					tempAddress.setId(list.get(0).getId());
					tempAddress.setIsDefault(Constants.DefaultFlag.NO.ordinal());
					addressService.updateAddress(tempAddress);
				}
			}
			String id=addressService.insert(userAddress)+"";
			return ResultModel.ok(id);
		} catch (Exception e) {
			logger.error("用户添加地址异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}

	@RequestMapping(value = "putAddress", method = RequestMethod.PUT)
	public ResultModel putAddress(String userId,  
			@RequestParam String id,
			@RequestParam String contact,
			@RequestParam String telephone,
			@RequestParam String address) {
		Assert.notNull(id, "id can not be empty");
		Assert.notNull(contact, "联系人 can not be empty");
		Assert.notNull(telephone, "联系电话 can not be empty");
		Assert.notNull(address, "详细地址 can not be empty");
		try {
			AddressVo addressVo = new AddressVo();
			addressVo.setContact(contact);
			addressVo.setTelephone(telephone);
			addressVo.setAddress(address);
			addressVo.setUserId(userId);
			addressVo.setId(id);
			addressService.updateAddress(addressVo);
			return ResultModel.ok();
		} catch (Exception e) {
			logger.error("用户修改地址异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
	
	@RequestMapping(value = "queryAddress", method = RequestMethod.GET)
	public ResultModel queryAddress(String userId) {
		AddressVo addressVo = new AddressVo();
		addressVo.setUserId(userId);
		List<UserAddress> data = addressService.queryAddress(addressVo);
		return ResultModel.ok(data);
	}
	
	@RequestMapping(value = "delAddress/{id}", method = RequestMethod.DELETE)
	public ResultModel delAddress(String userId,@PathVariable("id") String id) {
		try {
			addressService.updateDeleted(id, Constants.DelFlag.YES.ordinal());
			return ResultModel.ok();
		} catch (Exception e) {
			logger.error("用户删除地址异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
	
	
	@RequestMapping(value = "putDefault", method = RequestMethod.PUT)
	public ResultModel putDefault(String userId,@RequestParam String id) {
		Assert.notNull(id, "id can not be empty");
		try {
			addressService.updateDefault(id, userId);
			return ResultModel.ok();
		} catch (Exception e) {
			logger.error("用户设置默认地址异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
	
	
	
	
	
	
	
	
	

}
