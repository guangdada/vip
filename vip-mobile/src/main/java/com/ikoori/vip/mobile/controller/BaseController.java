package com.ikoori.vip.mobile.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.cache.Cache;
import com.ikoori.vip.common.constant.tips.SuccessTip;
import com.ikoori.vip.common.page.PageInfoBT;
import com.ikoori.vip.common.sms.Client;
import com.ikoori.vip.common.support.HttpKit;
import com.ikoori.vip.common.util.FileUtil;
import com.ikoori.vip.common.util.MD5;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;
import com.ikoori.vip.core.cache.CacheKit;

public class BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	protected static int pageSize = 10;
    protected static String SUCCESS = "SUCCESS";
    protected static String ERROR = "ERROR";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected static SuccessTip SUCCESS_TIP = new SuccessTip();

    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpKit.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }

    protected Integer getSystemInvokCount() {
        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
    }

    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfoBT<T> packForBT(Page<T> page) {
        return new PageInfoBT<T>(page);
    }

    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }

    /**
     * 删除cookie
     */
    protected void deleteCookieByName(String cookieName) {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) {
        byte[] bytes = FileUtil.toByteArray(filePath);
        return renderFile(fileName, bytes);
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }
    
    /**   
	 * @Title: getTbOrderByMobile   
	 * @date:   2017年9月28日 下午2:40:19 
	 * @author: huanglin
	 * @return: JSONArray      
	 * @throws   
	 */  
    protected JSONArray getTbOrderByMobile(String mobile) {
		String url = "http://api.ikoori.com:8899/dispatch/tbapi";
		Map<String, String> asObject = new TreeMap<String, String>();
		asObject.put("agent_id", "1");
		asObject.put("api", "getTbOrderByMobile");
		asObject.put("mobile", mobile);

		String secretkey = "lucy";

		String sign = MD5.signTopRequestNew(asObject, secretkey, false).toUpperCase();
		asObject.put("secretkey", secretkey);

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		NameValuePair p1 = new BasicNameValuePair("secretkey", secretkey);
		parameters.add(p1);

		NameValuePair p2 = new BasicNameValuePair("agent_id", asObject.get("agent_id"));
		parameters.add(p2);

		NameValuePair p3 = new BasicNameValuePair("api", asObject.get("api"));
		parameters.add(p3);

		NameValuePair p4 = new BasicNameValuePair("mobile", asObject.get("mobile"));
		parameters.add(p4);

		NameValuePair p5 = new BasicNameValuePair("sign", sign);
		parameters.add(p5);

		String result = HttpKit.sendAndReciveData(url, parameters);
		JSONObject jsonResult = JSONObject.parseObject(result);
		JSONArray orders = jsonResult.getJSONArray("data");
		
		return orders;
	}

	protected JSONArray getTbOrderByTid(String tid) {
		String url = "http://api.ikoori.com:8899/dispatch/tbapi";
		Map<String, String> asObject = new TreeMap<String, String>();
		asObject.put("agent_id", "1");
		asObject.put("api", "getTbOrderByTid");
		asObject.put("tid", tid);

		String secretkey = "lucy";

		String sign = MD5.signTopRequestNew(asObject, secretkey, false).toUpperCase();
		asObject.put("secretkey", secretkey);

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		NameValuePair p1 = new BasicNameValuePair("secretkey", secretkey);
		parameters.add(p1);

		NameValuePair p2 = new BasicNameValuePair("agent_id", asObject.get("agent_id"));
		parameters.add(p2);

		NameValuePair p3 = new BasicNameValuePair("api", asObject.get("api"));
		parameters.add(p3);

		NameValuePair p4 = new BasicNameValuePair("tid", asObject.get("tid"));
		parameters.add(p4);

		NameValuePair p5 = new BasicNameValuePair("sign", sign);
		parameters.add(p5);

		String result = HttpKit.sendAndReciveData(url, parameters);
		JSONObject jsonResult = JSONObject.parseObject(result);
		JSONArray orders = jsonResult.getJSONArray("data");
		return orders;
	}
	
	/** 
	* @Title: sendMessage 
	* @Description: 发送手机短信验证码
	* @param  request
	* @param  mobile  手机号 
	* @return void     
	* @throws 
	*/
	protected String sendMessage(String mobile) {
		log.info("进入sendMessage");
		int max = 999999;
		int min = 100000;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		String content = "【酷锐运动】您的激活验证码是：" + s;
		String result_mt = Client.me().mdSmsSend_u(mobile, content, "", "", "");
		log.info("短信发送结果>>" +result_mt);
		log.info("结束sendMessage");
		return s+"";
	}
	
	/**
	 * 获得小程序用户unionid
	 * @Title: getUnionid   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @date:   2017年11月13日 上午10:58:32 
	 * @author: chengxg
	 * @return: String      
	 * @throws
	 */
	protected String getUnionid(String sessionid, Map<String, Object> result) {
		if (StringUtils.isBlank(sessionid)) {
			log.error("sessionid不能为空");
			result.put("msg", "sessionid不能为空");
		}
		String unionid = CacheKit.get(Cache.XCXSESSIONID, sessionid);
		if (StringUtils.isBlank(unionid)) {
			log.error("没有找到sessionid>>" + sessionid);
			result.put("msg", "没有找到sessionid");
		}
		return unionid;
	}
}
