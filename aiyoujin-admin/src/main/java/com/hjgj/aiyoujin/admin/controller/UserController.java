//package com.hjgj.aiyoujin.admin.controller;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.hjgj.aiyoujin.admin.common.logger.LoggerProfile;
//import com.hjgj.aiyoujin.admin.api.IMemberForAdminApi;
//import com.hjgj.aiyoujin.admin.model.User;
//import com.hjgj.aiyoujin.admin.model.UserAddress;
//import com.hjgj.aiyoujin.admin.vo.AddressVo;
//import com.hjgj.aiyoujin.admin.vo.Page;
//import com.hjgj.aiyoujin.admin.vo.admin.BankCardInfoVo;
//
//@Controller
//@RequestMapping("/member")
//public class UserController extends BaseController {
//	
//	@Autowired
//	private IMemberForAdminApi memberForAdminApi;
//	
//	
//	
//	@RequestMapping("/userList.html")
//	public ModelAndView userList(ModelMap modelMap ,User user,Integer pageNum ,Integer pageSize){
//		pageNum = pageNum == null ? super.pageNum:pageNum;
//		pageSize = pageSize == null ? super.pageSize : this.pageSize;
//		Page<User> page = memberForAdminApi.queryPageUser(user, pageNum, pageSize);
//		ModelAndView mav = getModelAndView();
//		mav.addObject("page", page);
//		mav.addObject("user",  user);
//		mav.setViewName("user/userList");
//		return mav;
//	}
//	
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
//}
