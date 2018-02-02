package com.hjgj.aiyoujin.admin.controller;

import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.UserAddress;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.model.vo.UserAddressVo;
import com.hjgj.aiyoujin.core.model.vo.UserVO;
import com.hjgj.aiyoujin.core.service.AddressService;
import com.hjgj.aiyoujin.core.service.UserService;
import com.hjgj.aiyoujin.core.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @RequestMapping("/userList.html")
    public ModelAndView userList(ModelMap modelMap, User user, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : this.pageSize;
        Page<UserVO> page = userService.queryPageUser(user, pageNum, pageSize);
        ModelAndView mav = getModelAndView();
        mav.addObject("page", page);
        mav.addObject("user", user);
        mav.setViewName("user/userList");
        return mav;
    }

    @RequestMapping(value = "/addressEntry.html")
    public ModelAndView userAddressEntry(AddressVo addressVo, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : this.pageSize;
        Page<UserAddressVo> page = addressService.getAddressListVO(addressVo, pageNum, pageSize);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/userAddress");
        mav.addObject("page", page);
        mav.addObject("vo", addressVo);
        return mav;
    }

    @RequestMapping(value = "/viewAddress.html", method = RequestMethod.POST)
    public ModelAndView getUserAddressById(String addressId) {
        ModelAndView mav = new ModelAndView();
        UserAddress address = addressService.getUserAddressById(addressId);
        mav.addObject("address", address);
        mav.setViewName("order/pickOrder/viewAddress");
        return mav;
    }
//	@LoggerProfile(methodNote="冻结/解冻用户")
//	@ResponseBody
//	@RequestMapping("/updateFrozen")
//    public String updateFrozen(String userId, Integer frozen){
//        try{
//        	memberForAdminApi.frozenUser(userId, frozen);
//        	return SUCCESS;
//        }catch (Exception e){
//            e.printStackTrace();
//            return FAIL;
//        }
//    }
//	
//	@LoggerProfile(methodNote="锁定/解锁用户")
//	@ResponseBody
//	@RequestMapping("/updateLock")
//    public String updateLock(String userId, Integer lock){
//        try{
//        	memberForAdminApi.lockUser(userId, lock);
//        	return SUCCESS;
//        }catch (Exception e){
//            e.printStackTrace();
//            return FAIL;
//        }
//    }
//	
//	@RequestMapping("/userInfo.html")
//    public ModelAndView userInfo(HttpServletRequest request,String userId){
//        User user = memberForAdminApi.queryUserById(userId);
//        ModelAndView mav = getModelAndView();
//		mav.addObject("user",user);
//		mav.setViewName("user/userInfo");
//		return mav;
//    }
//
//	@RequestMapping("/userAddress.html")
//	public ModelAndView userAddressList(ModelMap modelMap ,AddressVo addressVo,Integer pageNum ,Integer pageSize){
//		pageNum = pageNum == null ? super.pageNum:pageNum;
//		pageSize = pageSize == null ? super.pageSize : this.pageSize;
//		Page<UserAddress> page = memberForAdminApi.queryAddress(addressVo, pageNum, pageSize);
//		ModelAndView mav = getModelAndView();
//		mav.addObject("page", page);
//		mav.addObject("addressVo", addressVo);
//		mav.setViewName("user/userAddress");
//		return mav;
//	}
//	
//	@RequestMapping("/userBankCard.html")
//	public ModelAndView userBankCard(ModelMap modelMap ,BankCardInfoVo bankCardInfoVo,Integer pageNum ,Integer pageSize){
//		pageNum = pageNum == null ? super.pageNum:pageNum;
//		pageSize = pageSize == null ? super.pageSize : this.pageSize;
//		Page<BankCardInfoVo> page = memberForAdminApi.queryPageUserBankCardVo(bankCardInfoVo, pageNum, pageSize);
//		ModelAndView mav = getModelAndView();
//		mav.addObject("page", page);
//		mav.addObject("bankCardInfoVo", bankCardInfoVo);
//		mav.setViewName("user/userBankCard");
//		return mav;
//	}
//	
//	@LoggerProfile(methodNote="解绑银行卡")
//	@RequestMapping("/unBundCard")
//    public String unBundCard(HttpServletRequest request,String id){
//        try{
//        	memberForAdminApi.unBindCard(id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "redirect:userBankCard.html";
//    }
}
