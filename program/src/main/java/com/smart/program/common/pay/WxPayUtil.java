package com.smart.program.common.pay;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class WxPayUtil {
	//


    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }


    /**
     * 元转换成分
     * @param money
     * @return
     */
    public static String getMoney(String amount) {
        if(amount==null){
            return "";
        }
        // 金额转化为分为单位
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
    }

    /**
     * 解析 回调时的xml装进map 返回
     * @param result 
     * @return
     * @throws DocumentException 
     */
    public static Map<String, String> getNotifyUrl(String result) throws DocumentException {
    	Map<String, String> map = new HashMap<String, String>();
        InputStream in = new ByteArrayInputStream(result.getBytes());
        SAXReader read = new SAXReader();
        Document doc = read.read(in);
        //得到xml根元素
        Element root = doc.getRootElement();
        //遍历  得到根元素的所有子节点 
        @SuppressWarnings("unchecked")
        List<Element> list =root.elements();
        for(Element element:list){
            //装进map
            map.put(element.getName().toString(), element.getText().toString());
        }
        return map;
    }

    /**
     * 验证签名，判断是否是从微信发过来
     * 验证方法：接收微信服务器回调我们url的时候传递的xml中的参数 然后再次加密，看是否与传递过来的sign签名相同
     * @param map
     * @return
     */
    public static boolean verifyWeixinNotify(Map<String, String> map, String key) {
        //根据微信服务端传来的各项参数 进行再一次加密后  与传过来的 sign 签名对比
    	map.remove("return_code");
    	map.remove("result_code");
    	map.remove("");
        String mapStr = PayUtil.createLinkString(map);
        String signOwn = PayUtil.sign(mapStr, key, "utf-8").toUpperCase();         //根据微信端参数进行加密的签名
        String signWx = map.get("sign");                //微信端传过来的签名
        if(signOwn.equals(signWx)){
            //如果两个签名一致，验证成功
            return true;
        }
        return false;
    }  
}
