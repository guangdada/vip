package com.ikoori.vip.api.service;

/**
 * 邀请注册api
 * @ClassName:  ShareApi
 * @author: chengxg
 * @date:   2017年10月16日 下午5:49:32
 */
public interface ShareApi {
	
	/**
	 * 保存邀请记录
	 * @Title: saveShareLog   
	 * @param shareUnionid 邀请人unionid
	 * @param receiveUnionid 受邀人unionid
	 * @param receiveIp 受邀人ip
	 * @return
	 * @throws Exception
	 * @date:   2017年10月16日 下午5:53:15 
	 * @author: chengxg
	 */
	public void saveShareLog(String shareUnionid, String receiveUnionid,String receiveIp) throws Exception;
}
