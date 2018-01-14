package com.hjgj.aiyoujin.core.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.UserAddressMapper;
import com.hjgj.aiyoujin.core.model.UserAddress;
import com.hjgj.aiyoujin.core.model.UserAddressExample;
import com.hjgj.aiyoujin.core.model.UserAddressExample.Criteria;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.vo.AddressVo;


@Service
public class AddressService extends BaseService  {
	
	@Autowired
	private UserAddressMapper	userAddressMapper;
	
	
	public int insert(UserAddress userAddress){
		userAddress.setId(UUIDGenerator.generate());
		userAddress.setCreateTime(new Date());
		if(userAddress.getIsDefault() == null){
			userAddress.setIsDefault(Constants.DefaultFlag.NO.ordinal());
		}
		return userAddressMapper.insertSelective(userAddress);
	}
	
	
	public List<UserAddress> queryAddress(AddressVo addressVo){
		UserAddressExample example = new UserAddressExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(addressVo.getUserId())){
			criteria.andUserIdEqualTo(addressVo.getUserId());
		}
//		if(addressVo.getDeleted() != null){
			criteria.andDeletedEqualTo(Constants.DelFlag.NO.ordinal());
//		}
		if(addressVo.getIsDefault() != null){
			criteria.andIsDefaultEqualTo(addressVo.getIsDefault());
		}
		return userAddressMapper.selectByExample(example);
	}
	
	public Page<UserAddress> queryPageUserAddress(AddressVo addressVo,Integer pageNum, Integer pageSize){
		Page<UserAddress> page = new Page<UserAddress>(pageNum,pageSize,true);
		UserAddressExample example = new UserAddressExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(addressVo.getTelephone())){
			criteria.andTelephoneEqualTo(addressVo.getTelephone());
		}
		if(StringUtils.isNotBlank(addressVo.getContact())){
			criteria.andContactEqualTo(addressVo.getContact());
		}
		example.setOrderByClause("create_time desc");
		int total = userAddressMapper.countByExample(example);
		if(total > 0){
			example.setLimitOffset(page.getStartRow());
			example.setLimitRows(pageSize);
			List<UserAddress>  list = userAddressMapper.selectByExample(example);
			page.setTotal(total);
			page.setList(list);
		}
		return page;
	}
	
	
	public int updateAddress(AddressVo addressVo){
		UserAddress userAddress = new UserAddress();
		if(StringUtils.isNotBlank(addressVo.getId())){
			userAddress.setId(addressVo.getId());
		}
		if(addressVo.getIsDefault() != null){
			userAddress.setIsDefault(addressVo.getIsDefault());
		}
		if(StringUtils.isNotBlank(addressVo.getAddress())){
			userAddress.setAddress(addressVo.getAddress());
		}
		if(StringUtils.isNotBlank(addressVo.getAreaId())){
			userAddress.setAreaId(addressVo.getAreaId());
		}
		if(StringUtils.isNotBlank(addressVo.getContact())){
			userAddress.setContact(addressVo.getContact());
		}
		if(StringUtils.isNotBlank(addressVo.getPostCode())){
			userAddress.setPostCode(addressVo.getPostCode());
		}
		if(StringUtils.isNotBlank(addressVo.getTelephone())){
			userAddress.setTelephone(addressVo.getTelephone());
		}
		if(addressVo.getDeleted() != null){
			userAddress.setDeleted(addressVo.getDeleted());
		}
		userAddress.setUpdateTime(new Date());
		return userAddressMapper.updateByPrimaryKeySelective(userAddress);
	}
	
	@Transactional
	public int updateDefault(String id,String userId){
		UserAddressExample example = new UserAddressExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDefaultEqualTo(Constants.DefaultFlag.YES.ordinal());
		criteria.andUserIdEqualTo(userId);
		UserAddress userAddress = new UserAddress();
		userAddress.setIsDefault(0);
		userAddressMapper.updateByExampleSelective(userAddress, example);
		example.clear();
		criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		criteria.andUserIdEqualTo(userId);
		userAddress.setIsDefault(Constants.DefaultFlag.YES.ordinal());
		return userAddressMapper.updateByExampleSelective(userAddress,example);
	}
	
	public int updateDeleted(String id,Integer deleted){
		UserAddressExample example = new UserAddressExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		UserAddress userAddress = new UserAddress();
		userAddress.setDeleted(deleted);
		return userAddressMapper.updateByExampleSelective(userAddress,example);
	}

}
