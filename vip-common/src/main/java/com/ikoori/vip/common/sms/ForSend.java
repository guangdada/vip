package com.ikoori.vip.common.sms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForSend {

	public static void SendSms() {
		int i = 0;
		while (true) {
			try {
				if (Send.list.size() > 0) {
					System.out.println("发现新消息了");
					for (i = 0; i < Send.list.size(); i++) {
						String regExp = "^[1][3,4,5,8,7][0-9]{9}$";
						Pattern p = Pattern.compile(regExp);
						SendInfo sin = Send.list.get(i);
						String mobiles = sin.getMoblie();
						Matcher m = p.matcher(mobiles);// 验证是否为手机号
						if (!m.matches()) {
							Send.list.remove(i);
							continue;
						}
						// 短信发送
						System.out.println("发送短信:"+mobiles+"   "+sin.getContent());
						String result_mt = Client.me().mdSmsSend_u(mobiles, sin.getContent(), "", "", "");
						if (result_mt.startsWith("-") || result_mt.equals(""))// 发送短信，如果是以负号开头就是发送失败。
						{
							System.out.print("发送失败！返回值为：" + result_mt + "请查看webservice返回值对照表");
							return;
						}
						// 输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
						else {
							System.out.print("发送成功，返回值为：" + result_mt);
						}
						Send.list.remove(i);// 删除已经执行完的数据对象
						Thread.sleep(3000);

					}
				} else {
					//System.out.println("无推送数据");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Send.list.remove(i);
			} finally {
			}

		}

	}
	
	public static void main(String[] args) {
		Send.list.add(new SendInfo("18508443775", "亲，好评并微信关注“kryd2015”公众号"));
		ForSend.SendSms();
	}

}