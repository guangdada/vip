package com.ikoori.vip.common.message;

import java.net.URLEncoder;

/**
 * <p>
 * <date>2012-03-01</date><br/>
 * <span>软维提供的JAVA接口信息（短信，彩信）调用API</span><br/>
 * <span>----------发送短信-------------</span>
 * </p>
 * 
 * @author LIP
 * @version 1.0.1
 */
public class SmsClientSend {

	/**
	 * <p>
	 * <date>2012-03-01</date><br/>
	 * <span>发送信息方法1--必须传入必填内容</span><br/>
	 * <p>
	 * 其一：发送方式，默认为POST<br/>
	 * 其二：发送内容编码方式，默认为UTF-8
	 * </p>
	 * <br/>
	 * </p>
	 * 
	 * @param url
	 *            ：必填--发送连接地址URL--比如>http://inter.smswang.net:7803/sms
	 * @param account
	 *            ：必填--用户帐号
	 * @param password
	 *            ：必填--用户密码
	 * @param mobile
	 *            ：必填--发送的手机号码，多个可以用逗号隔比如>13512345678,13612345678
	 * @param content
	 *            ：必填--实际发送内容，
	 * @param extno
	 *            ：必填--接入码，为数字
	 * @return 返回发送信息之后返回字符串
	 */
	public static String sms(String url, String account,
			String password, String mobile, String content,
			String extno) {

		try {
			account = URLEncoder.encode(account, "UTF-8");
			password = URLEncoder.encode(password, "UTF-8");
			StringBuffer send = new StringBuffer();
			send.append("action=send");
			send.append("&account=").append(account);
			send.append("&password=").append(password);
			send.append("&mobile=").append(mobile);
			send.append("&content=").append(URLEncoder.encode(content, "UTF-8"));
			send.append("&extno=").append(extno);
			return SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
					send.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "未发送，编码异常";
		}
	}

}
