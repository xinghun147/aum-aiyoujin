package com.hjgj.aiyoujin.admin.common.utils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;    
    
public class FormatCheckUtils {    
    
    /**  
     * 大陆号码或香港号码均可  
     */    
    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {    
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);    
    }    
    
    /**  
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数  
     * 此方法中前三位格式有：  
     * 13+任意数  
     * 15+除4的任意数  
     * 18+除1和4的任意数  
     * 17+除9的任意数  
     * 147  
     */    
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {    
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";    
        Pattern p = Pattern.compile(regExp);    
        Matcher m = p.matcher(str);    
        return m.matches();    
    }    
    
    /**  
     * 香港手机号码8位数，5|6|8|9开头+7位任意数  
     */    
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {    
        String regExp = "^(5|6|8|9)\\d{7}$";    
        Pattern p = Pattern.compile(regExp);    
        Matcher m = p.matcher(str);    
        return m.matches();    
    }  
    
    /** 
     * 18位身份证校验,粗略的校验 
     * @author lyl 
     * @param idCard 
     * @return 
     */  
    public static boolean is18ByteIdCard(String idCard){  
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$"); //粗略的校验  
        Matcher matcher = pattern1.matcher(idCard);  
        if(matcher.matches()){  
            return true;  
        }  
        return false;  
    }  
    
    
    /** 
     * 18位身份证校验,比较严格校验 
     * @author lyl 
     * @param idCard 
     * @return 
     */  
    public static boolean is18ByteIdCardComplex(String idCard){  
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");   
        Matcher matcher = pattern1.matcher(idCard);  
        int[] prefix = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};  
        int[] suffix = new int[]{ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };  
        if(matcher.matches()){  
            Map<String, String> cityMap = initCityMap();  
            if(cityMap.get(idCard.substring(0,2)) == null ){  
                return false;  
            }  
            int idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和  
            for(int i=0;i<17;i++){  
                idCardWiSum+=Integer.valueOf(idCard.substring(i,i+1))*prefix[i];  
            }  
              
            int idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置  
            String idCardLast=idCard.substring(17);//得到最后一位身份证号码  
              
            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X  
            if(idCardMod==2){  
                if(idCardLast.equalsIgnoreCase("x")){  
                    return true;  
                }else{  
                    return false;  
                }  
            }else{  
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码  
                if(idCardLast.equals(suffix[idCardMod]+"")){  
                    return true;  
                }else{  
                    return false;  
                }  
           }  
        }  
        return false;  
    }  
      
    private static Map<String, String> initCityMap(){  
        Map<String, String> cityMap = new HashMap<String, String>();  
            cityMap.put("11", "北京");  
            cityMap.put("12", "天津");  
            cityMap.put("13", "河北");  
            cityMap.put("14", "山西");  
            cityMap.put("15", "内蒙古");  
              
            cityMap.put("21", "辽宁");  
            cityMap.put("22", "吉林");  
            cityMap.put("23", "黑龙江");  
              
            cityMap.put("31", "上海");  
            cityMap.put("32", "江苏");  
            cityMap.put("33", "浙江");  
            cityMap.put("34", "安徽");  
            cityMap.put("35", "福建");  
            cityMap.put("36", "江西");  
            cityMap.put("37", "山东");  
              
            cityMap.put("41", "河南");  
            cityMap.put("42", "湖北");  
            cityMap.put("43", "湖南");  
            cityMap.put("44", "广东");  
            cityMap.put("45", "广西");  
            cityMap.put("46", "海南");  
              
            cityMap.put("50", "重庆");  
            cityMap.put("51", "四川");  
            cityMap.put("52", "贵州");  
            cityMap.put("53", "云南");  
            cityMap.put("54", "西藏");  
              
            cityMap.put("61", "陕西");  
            cityMap.put("62", "甘肃");  
            cityMap.put("63", "青海");  
            cityMap.put("64", "宁夏");  
            cityMap.put("65", "新疆");  
              
//          cityMap.put("71", "台湾");  
//          cityMap.put("81", "香港");  
//          cityMap.put("82", "澳门");  
//          cityMap.put("91", "国外");  
//          System.out.println(cityMap.keySet().size());  
            return cityMap;  
        }  
    
    /** 
     * 匹配Luhn算法：可用于检测银行卡卡号 
     * @param cardNo 
     * @return 
     * */ 
    public static boolean matchLuhn(String cardNo){ 
    	int[] cardNoArr = new int[cardNo.length()]; 
    	for (int i=0; i<cardNo.length(); i++) { 
    		cardNoArr[i] = Integer.valueOf(String.valueOf(cardNo.charAt(i))); 
    	} 
    	for(int i=cardNoArr.length-2;i>=0;i-=2) {
    		cardNoArr[i] <<= 1; cardNoArr[i] = cardNoArr[i]/10 + cardNoArr[i]%10; 
    	} 
    	int sum = 0; 
    	for(int i=0;i<cardNoArr.length;i++) {
    		sum += cardNoArr[i]; 
    	} 
    	return sum % 10 == 0; 
    }
}    