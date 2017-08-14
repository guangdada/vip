package com.ikoori.vip.server.modular.biz.warpper;

import java.util.List;
import java.util.Map;

import com.ikoori.vip.common.constant.state.GrantType;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.warpper.BaseControllerWarpper;
import com.ikoori.vip.server.common.constant.factory.ConstantFactory;

/**
 * 用户管理的包装类
 *
 * @author chengxg
 * @date 2017年2月13日 下午10:47:03
 */
public class MemberCardWarpper extends BaseControllerWarpper {

    public MemberCardWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	Long cardId = Long.valueOf(map.get("card_id").toString());
    	Long memberId = Long.valueOf(map.get("member_id").toString());
    	Integer state = Integer.valueOf(map.get("state").toString());
    	Card card = ConstantFactory.me().getCard(cardId);
    	Member member = ConstantFactory.me().getMember(memberId);
    	map.put("mobile", member.getMobile());
    	map.put("cardName", card == null ? "-" : card.getName());
    	map.put("grantType", card == null ? "-" : GrantType.valueOf(card.getGrantType()));
    	map.put("state", MemCardState.valueOf(state));
    }

}
