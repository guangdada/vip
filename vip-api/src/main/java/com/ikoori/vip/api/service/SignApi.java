package com.ikoori.vip.api.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 签到
 * @ClassName:  SignApi
 * @author: chengxg
 * @date:   2017年9月29日 上午10:35:09
 */
public interface SignApi {
	/**
	 * 每日签到
	 * @Title: signIn   
	 * @param openId
	 * @return
	 * @throws Exception
	 * @date:   2017年9月29日 下午4:23:27 
	 * @author: chengxg
	 */
	public JSONObject signIn(String openId) throws Exception;
	/**
	 * 获得签到信息
	 * @Title: getSignInfo   
	 * @param openId
	 * @return
	 * @throws Exception
	 * @date:   2017年9月29日 下午4:23:36 
	 * @author: chengxg
	 */
	public JSONObject getSignInfo(String openId) throws Exception;
}
