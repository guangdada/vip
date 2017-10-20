package com.ikoori.vip.server.core.util;

import com.coolfish.weixin.pay.WXPay;
import com.coolfish.weixin.pay.common.PayConfig;
import com.coolfish.weixin.pay.common.Util;
import com.coolfish.weixin.redpack.business.listener.RedPackSendListener;
import com.coolfish.weixin.redpack.protocol.RedPackResData;
import com.coolfish.weixin.redpack.protocol.send.RedPackSendReqData;
import com.coolfish.weixin.redpack.protocol.send.RedPackSendResData;

public class RedPackUtil {
	public static RedPackResData redPackSend(int amount, String actName, String remark, String wishing, String openid,
			String billno,String certLocalPath) {
		try {
			final RedPackResData rprd = new RedPackResData();
			// --------------------------------------------------------------------
			// 温馨提示，第一次使用该SDK时请到com.tencent.common.Configure类里面进行配置
			// --------------------------------------------------------------------

			// certLocalPath /data/apps/weixin/apiclient_cert.p12
			WXPay.initSDKConfiguration("JTZM88CKRKSWNUAUUF8HMJ7FJPGIY3NA", "wx71e66431bed0303e", "1229321102", "",certLocalPath, "1229321102");
			// Configure.setAppID("wx71e66431bed0303e");
			// Configure.setKey("JTZM88CKRKSWNUAUUF8HMJ7FJPGIY3NA");
			// Configure.setMchID("1229321102");
			PayConfig.setIp(Util.localIp());
			PayConfig.setHttpsRequestClassName("com.coolfish.weixin.pay.common.HttpsRequest");
			// Configure.setCertLocalPath(certLocalPath);
			// Configure.setCertPassword(certPassword);
			// --------------------------------------------------------------------
			// 温馨提示，统一下单，获得付款地址
			// --------------------------------------------------------------------
			RedPackSendReqData sendRedPackReqData = new RedPackSendReqData(billno, "酷锐运动", openid, amount, 1, wishing,
					PayConfig.getIP(), actName, remark);

			// 也可以直接调用服务，自己处理error_code
			// UnifiedOrderResData unifiedOrderResData=WXPay.requestUnifiedOrderService(unifiedOrderReqData);
			// System.out.println(unifiedOrderResData);
			// 封装好错误的商业服务
			WXPay.doRedPackSendBusiness(sendRedPackReqData, new RedPackSendListener() {

				public void onXmlError(RedPackResData payResData) {
					rprd.setErr_code(payResData.getErr_code());
					rprd.setErr_code_des(payResData.getErr_code_des());
					rprd.setResult_code(payResData.getResult_code());
					rprd.setReturn_code(payResData.getReturn_code());
					rprd.setReturn_msg(payResData.getReturn_msg());
				}

				public void onSystemError(RedPackResData payResData) {
					rprd.setErr_code(payResData.getErr_code());
					rprd.setErr_code_des(payResData.getErr_code_des());
					rprd.setResult_code(payResData.getResult_code());
					rprd.setReturn_code(payResData.getReturn_code());
					rprd.setReturn_msg(payResData.getReturn_msg());
				}

				public void onSignError(RedPackResData payResData) {
					rprd.setErr_code(payResData.getErr_code());
					rprd.setErr_code_des(payResData.getErr_code_des());
					rprd.setResult_code(payResData.getResult_code());
					rprd.setReturn_code(payResData.getReturn_code());
					rprd.setReturn_msg(payResData.getReturn_msg());
				}

				public void onParamError(RedPackResData payResData) {
					rprd.setErr_code(payResData.getErr_code());
					rprd.setErr_code_des(payResData.getErr_code_des());
					rprd.setResult_code(payResData.getResult_code());
					rprd.setReturn_code(payResData.getReturn_code());
					rprd.setReturn_msg(payResData.getReturn_msg());

				}

				public void onNoAuth(RedPackResData payResData) {
					rprd.setErr_code(payResData.getErr_code());
					rprd.setErr_code_des(payResData.getErr_code_des());
					rprd.setResult_code(payResData.getResult_code());
					rprd.setReturn_code(payResData.getReturn_code());
					rprd.setReturn_msg(payResData.getReturn_msg());
				}

				public void onFreqLimit(RedPackResData payResData) {
					rprd.setErr_code(payResData.getErr_code());
					rprd.setErr_code_des(payResData.getErr_code_des());
					rprd.setResult_code(payResData.getResult_code());
					rprd.setReturn_code(payResData.getReturn_code());
					rprd.setReturn_msg(payResData.getReturn_msg());
				}

				public void onCaError(RedPackResData payResData) {
					rprd.setErr_code(payResData.getErr_code());
					rprd.setErr_code_des(payResData.getErr_code_des());
					rprd.setResult_code(payResData.getResult_code());
					rprd.setReturn_code(payResData.getReturn_code());
					rprd.setReturn_msg(payResData.getReturn_msg());
				}

				public void onSystemBusinessError(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());
				}

				public void onSuccess(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());
				}

				public void onSendNumLimit(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());

				}

				public void onSendFailed(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());

				}

				public void onOtherError(RedPackSendResData SendRedPackResData) {
					rprd.setErr_code(SendRedPackResData.getErr_code());
					rprd.setErr_code_des(SendRedPackResData.getErr_code_des());
					rprd.setResult_code(SendRedPackResData.getResult_code());
					rprd.setReturn_code(SendRedPackResData.getReturn_code());
					rprd.setReturn_msg(SendRedPackResData.getReturn_msg());
				}

				public void onOpenidError(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());

				}

				public void onNotenough(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());
				}

				public void onMoneyLimit(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());
				}

				public void onIllegalAppid(RedPackSendResData SendRedPackResData) {
					rprd.setErr_code(SendRedPackResData.getErr_code());
					rprd.setErr_code_des(SendRedPackResData.getErr_code_des());
					rprd.setResult_code(SendRedPackResData.getResult_code());
					rprd.setReturn_code(SendRedPackResData.getReturn_code());
					rprd.setReturn_msg(SendRedPackResData.getReturn_msg());
				}

				public void onFatalError(RedPackSendResData redPackResData) {
					rprd.setErr_code(redPackResData.getErr_code());
					rprd.setErr_code_des(redPackResData.getErr_code_des());
					rprd.setResult_code(redPackResData.getResult_code());
					rprd.setReturn_code(redPackResData.getReturn_code());
					rprd.setReturn_msg(redPackResData.getReturn_msg());
				}
			});
			return rprd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
