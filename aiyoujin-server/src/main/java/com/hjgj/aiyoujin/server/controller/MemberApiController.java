package com.hjgj.aiyoujin.server.controller;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.common.utils.FormatCheckUtils;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.UserAddress;
import com.hjgj.aiyoujin.core.service.AddressService;
import com.hjgj.aiyoujin.core.vo.AddressVo;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member/")
public class MemberApiController {
    protected final Logger logger = LoggerFactory.getLogger(MemberApiController.class);
    @Autowired
    private AddressService addressService;


    @ApiOperation(value = "添加地址")
    @RequestMapping(value = "addAddress", method = RequestMethod.POST)
    public ResultModel addAddress(String userId,
                                  @RequestParam String contact,
                                  @RequestParam String telephone,
                                  @RequestParam String address) {
        Assert.notNull(contact, "联系人 can not be empty");
        Assert.notNull(telephone, "联系电话 can not be empty");
        Assert.notNull(address, "详细地址 can not be empty");
        if (!FormatCheckUtils.isPhoneLegal(telephone)) {//手机号格式校验
            return ResultModel.error(ResultStatus.USER_PHONE_ERROR);
        }
        try {
            AddressVo addressVo = new AddressVo();
            addressVo.setContact(contact);
            addressVo.setTelephone(telephone);
            addressVo.setAddress(address);
            addressVo.setUserId(userId);
            UserAddress userAddress = new UserAddress();
//            userAddress.setId(UUIDGenerator.generate());
            userAddress.setAddress(addressVo.getAddress());
            userAddress.setAreaId(addressVo.getAreaId());
            userAddress.setContact(addressVo.getContact());
            userAddress.setIsDefault(addressVo.getIsDefault());
            userAddress.setPostCode(addressVo.getPostCode());
            userAddress.setTelephone(addressVo.getTelephone());
            userAddress.setUserId(addressVo.getUserId());
            userAddress.setDeleted(Constants.DelFlag.NO.ordinal());
            if (addressVo.getIsDefault() != null && addressVo.getIsDefault() == Constants.DefaultFlag.YES.ordinal()) {
                AddressVo tempAddress = new AddressVo();
                tempAddress.setUserId(addressVo.getUserId());
                tempAddress.setIsDefault(Constants.DefaultFlag.YES.ordinal());
                List<UserAddress> list = addressService.queryAddress(tempAddress);
                if (list != null && list.size() > 0) {
                    tempAddress.setId(list.get(0).getId());
                    tempAddress.setIsDefault(Constants.DefaultFlag.NO.ordinal());
                    addressService.updateAddress(tempAddress);
                }
            }
            String id = addressService.insert(userAddress) + "";
            return ResultModel.ok(userAddress.getId());
        } catch (Exception e) {
            logger.error("用户添加地址异常,e:{}", e);
            return ResultModel.error(ResultStatus.SYSTEM_ERROR);
        }
    }

    @ApiOperation(value = "修改地址")
    @RequestMapping(value = "putAddress", method = RequestMethod.POST)
    public ResultModel putAddress(@ApiParam(value = "用户Id", required = true) String userId,
                                  @ApiParam(value = "地址ID", required = true) String id,
                                  @ApiParam(value = "联系人") @RequestParam(required = false) String contact,
                                  @ApiParam(value = "手机号") @RequestParam(required = false) String telephone,
                                  @ApiParam(value = "联系地址") @RequestParam(required = false) String address) {
        Assert.notNull(id, "id can not be empty");
        Assert.notNull(contact, "联系人 can not be empty");
        Assert.notNull(telephone, "联系电话 can not be empty");
        Assert.notNull(address, "详细地址 can not be empty");
        try {
            System.out.println("hhhhhhhhh");
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

    @ApiOperation(value = "查询用户地址")
    @RequestMapping(value = "queryAddress", method = RequestMethod.GET)
    public ResultModel queryAddress(@ApiParam(value = "用户Id", required = true) String userId) {
        AddressVo addressVo = new AddressVo();
        addressVo.setUserId(userId);
        List<UserAddress> data = addressService.queryAddress(addressVo);
        return ResultModel.ok(data);
    }

    // @ApiParam(value = "用户Id", required = false)
    @ApiOperation(value = "删除用户地址")
    @RequestMapping(value = "delAddress/{id}", method = RequestMethod.POST)
    public ResultModel delAddress(String userId, @PathVariable("id") String id) {
        try {
            addressService.updateDeleted(id, Constants.DelFlag.YES.ordinal());
            return ResultModel.ok();
        } catch (Exception e) {
            logger.error("用户删除地址异常,e:{}", e);
            return ResultModel.error(ResultStatus.SYSTEM_ERROR);
        }
    }

    @ApiOperation(value = "设置默认地址")
    @RequestMapping(value = "putDefault", method = RequestMethod.POST)
    public ResultModel putDefault(@ApiParam(value = "用户Id", required = true) String userId,
                                  @ApiParam(value = "地址ID", required = true) String id) {
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
