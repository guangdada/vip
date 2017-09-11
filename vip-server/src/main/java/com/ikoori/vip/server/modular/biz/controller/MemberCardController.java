package com.ikoori.vip.server.modular.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.constant.state.GrantType;
import com.ikoori.vip.common.constant.state.MemCardState;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICardService;
import com.ikoori.vip.server.modular.biz.service.IMemberCardService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.warpper.MemberCardWarpper;

/**
 * 领取记录控制器
 *
 * @author chengxg
 * @Date 2017-08-14 13:14:54
 */
@Controller
@RequestMapping("/memberCard")
public class MemberCardController extends BaseController {

    private String PREFIX = "/biz/memberCard/";
    @Autowired
	IMemberCardService memberCardService;
    @Autowired
    IMerchantService merchantService;
    @Autowired
    ICardService cardService;

    /**
     * 跳转到领取记录首页
     */
    @Permission
    @RequestMapping("")
    public String index(Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("merchantId", merchant.getId());
    	
    	model.addAttribute("grantType", GrantType.values());
    	
    	model.addAttribute("state", MemCardState.values());
    	
    	// 查询会员卡
    	List<Card> cards = cardService.selectByCondition(condition);
    	model.addAttribute("cards", cards);
        return PREFIX + "memberCard.html";
    }

    /**
     * 跳转到添加领取记录
     */
    @Permission
    @RequestMapping("/memberCard_add")
    public String memberCardAdd() {
        return PREFIX + "memberCard_add.html";
    }

    /**
     * 跳转到修改领取记录
     */
    @Permission
    @RequestMapping("/memberCard_update/{memberCardId}")
    public String memberCardUpdate(@PathVariable Long memberCardId, Model model) {
    	MemberCard memberCard = memberCardService.selectById(memberCardId);
    	model.addAttribute(memberCard);
        return PREFIX + "memberCard_edit.html";
    }

    /**
     * 获取领取记录列表
     */
    @Permission
    @RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String cardNumber, String cardName, Long cardId, String mobile, String nickname, Integer state,
			Integer grantType) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Page<MemberCard> page = new PageFactory<MemberCard>().defaultPage();
		List<Map<String, Object>> result = memberCardService.getMemberCardList(cardId, grantType, state, mobile, page,
				nickname, page.getOrderByField(), page.isAsc(), merchant.getId(), cardNumber);
		page.setRecords((List<MemberCard>) new MemberCardWarpper(result).warp());
		return super.packForBT(page);
	}

    /**
     * 新增领取记录
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(MemberCard memberCard) {
    	memberCardService.insert(memberCard);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除领取记录
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long memberCardId) {
        memberCardService.deleteById(memberCardId);
        return SUCCESS_TIP;
    }


    /**
     * 修改领取记录
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(MemberCard memberCard) {
    	if (ToolUtil.isEmpty(memberCard) || memberCard.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	memberCardService.updateById(memberCard);
        return super.SUCCESS_TIP;
    }

    /**
     * 领取记录详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
