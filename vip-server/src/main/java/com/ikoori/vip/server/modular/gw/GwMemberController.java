package com.ikoori.vip.server.modular.gw;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.vo.UserInfo;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.util.WXPayUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.util.WeChatAPI;
import com.ikoori.vip.server.modular.biz.service.IMemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 会员接口
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
@Controller
@RequestMapping("/gw/member")
public class GwMemberController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IMemberService memberService;
	@Autowired
	GunsProperties gunsProperties;

	@ApiOperation("根据openid获得会员信息")
	@RequestMapping(value = "getMember", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMember(
			@ApiParam(value = "openid", required = true) @RequestParam(required = true) String openid,
			@ApiParam(value = "签名", required = true) @RequestParam(required = true) String sign) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("msg", "请求成功");
		try {
			boolean isSign = true;
			if (gunsProperties.isCheckSign()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("openid", openid);
				data.put("sign", sign);
				isSign = WXPayUtil.isSignatureValid(data, gunsProperties.getSignKey());
				if (!isSign) {
					result.put("code", "500");
					result.put("msg", "签名失败");
				}
			}
			if (isSign) {
				/*Map<String, Object> member = null;
				UserInfo userInfo = WeChatAPI.getUserInfo(openid);
				if (userInfo == null || userInfo.getUnionid() == null) {
					result.put("code", "500");
					result.put("msg", "没有找到该openid的微信用户");
				} else {
					member = memberService.getWxUserByUnionid(userInfo.getUnionid());
					if (member == null) {
						memberService.saveMember(userInfo, true);
						member = memberService.getWxUserByUnionid(userInfo.getUnionid());
					}
				}*/
				Map<String, Object> member = memberService.getWxUserByOpenid(openid);
				if(member == null){
					UserInfo userInfo = WeChatAPI.getUserInfo(openid);
					if(userInfo != null && userInfo.getUnionid() != null){
						member = memberService.getWxUserByUnionid(userInfo.getUnionid());
						if(member == null){
							memberService.saveMember(userInfo, true);
							member = memberService.getWxUserByUnionid(userInfo.getUnionid());
						}
					}
				}
				
				if(member == null){
					result.put("code", "500");
					result.put("msg", "没有找到该openid的微信用户");
				}

				if (member != null) {
					JSONObject obj = new JSONObject();
					obj.put("mobile", member.get("mobile"));
					obj.put("sex", member.get("sex"));
					obj.put("point", member.get("points"));
					obj.put("headImg", member.get("headimgurl"));
					obj.put("nickname", member.get("nickname"));
					obj.put("discount", 100);
					JSONObject card = memberService.initCard(Long.valueOf(member.get("id").toString()));
					if (card != null && card.get("state").toString().equals(MemCardState.USED.getCode() + "")) {
						JSONArray rights = card.getJSONArray("cardRights");
						for (int i = 0; i < rights.size(); i++) {
							JSONObject o = rights.getJSONObject(i);
							if (o.containsKey(RightType.DISCOUNT.getCode())) {
								Integer discount = o.getInteger(RightType.DISCOUNT.getCode());
								obj.put("discount", discount);
							}
						}
						obj.put("newDiscount", card.getString("newDiscount"));
						obj.put("limitAmount", card.getString("limitAmount"));
						obj.put("availableAmount", card.getString("availableAmount"));
					} else {
						log.info("没有找到会员卡，或者会员卡已经过期>>openid=" + openid);
					}
					result.put("content", obj);
				}
			}
		} catch (Exception e) {
			log.error("", e);
			result.put("code", "500");
			result.put("msg", "请求失败");
		}
		return result;
	}
}
