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
import com.ikoori.vip.common.constant.state.CardGrantType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.MemberCard;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICardService;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.warpper.MemberWarpper;

/**
 * 会员控制器
 *
 * @author chengxg
 * @Date 2017-08-02 12:31:41
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

    private String PREFIX = "/biz/member/";
    @Autowired
	IMemberService memberService;
    @Autowired
	MemberCardMapper memberCardMapper;
    @Autowired
	ICardService cardService;
    @Autowired
	IMerchantService merchantService;
    /**
     * 跳转到会员首页
     */
    @Permission
    @RequestMapping("")
	public String index(Model model) {
		// 查询会员卡
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());
		List<Card> cards = cardService.findByCondition(condition);
		model.addAttribute("cards", cards);
		return PREFIX + "member.html";
	}

    /**
     * 跳转到添加会员
     */
    @Permission
    @RequestMapping("/member_add")
    public String memberAdd(Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("merchantId", merchant.getId());
    	List<Card> cards=cardService.selectByCondition(condition);
    	//会员卡
    	model.addAttribute("cards",cards);
        return PREFIX + "member_add.html";
    }

    /**
     * 跳转到修改会员
     */
    @Permission
    @RequestMapping("/member_update/{memberId}")
    public String memberUpdate(@PathVariable Long memberId, Model model) {
    	Member member = memberService.selectById(memberId);
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	
    	MemberCard memberCard=new MemberCard();
    	memberCard.setMemberId(member.getId());
    	memberCard.setIsDefault(true);
    	memberCard=memberCardMapper.selectOne(memberCard);
    	model.addAttribute("memberCard",memberCard);
    	
    	Card card=cardService.selectById(memberCard.getCardId());
    	
    	//会员卡
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("merchantId", merchant.getId());
    	condition.put("grantType",CardGrantType.NO_RULE.getCode());
    	List<Card> cards=cardService.selectByCondition(condition);
    	model.addAttribute("cards",cards);
    	
    	model.addAttribute("member",member);
        return PREFIX + "member_edit.html";
    }

    /**
     * 获取会员列表
     */
	@RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String memName,String memMobile,Integer memSex,String memNickName,Long cardId,String cardNumber,Integer isActive,String openId) {
		Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
		List<Map<String, Object>> result = memberService.getMemberList(page, memName, memSex, memNickName, memMobile,
				cardId, cardNumber,isActive,openId,page.getOrderByField(), page.isAsc());
		page.setRecords((List<Map<String, Object>>) new MemberWarpper(result).warp());
		return super.packForBT(page);
    }

    /**
     * 新增会员
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Member member,Long cardId) {
    	//添加会员， 判断账号是否重复
    	Member memberRe=memberService.selectByMobile(member.getMobile());
    	if(memberRe!=null){
    		 throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
    	}
    	memberService.saveMember(member, cardId);
        return super.SUCCESS_TIP;
    }
    
    /**
     * 删除会员
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long memberId) {
       memberService.deleteMember(memberId);
        return SUCCESS_TIP;
    }
    
    /**
     * 修改会员
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Member member,Long cardId,int point) {
    	if (ToolUtil.isEmpty(member) || member.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	memberService.updateMember(member, cardId,point);
        return super.SUCCESS_TIP;
    }

    /**
     * 会员详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
