package com.ikoori.vip.server.modular.biz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.Const;
import com.ikoori.vip.common.constant.factory.PageFactory;
import com.ikoori.vip.common.constant.state.CardGrantType;
import com.ikoori.vip.common.constant.state.ColorType;
import com.ikoori.vip.common.constant.state.RightType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.CardRight;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.util.ToolUtil;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.ICardRightService;
import com.ikoori.vip.server.modular.biz.service.ICardService;
import com.ikoori.vip.server.modular.biz.service.ICouponService;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;
import com.ikoori.vip.server.modular.biz.warpper.CardWarpper;

/**
 * 会员卡控制器
 *
 * @author chengxg
 * @Date 2017-08-04 11:07:42
 */
@Controller
@RequestMapping("/card")
public class CardController extends BaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    private String PREFIX = "/biz/card/";
    @Autowired
	ICardService cardService;
    
    @Autowired
    IMerchantService merchantService;
    
    @Autowired
    
    ICouponService couponService;
    
    @Autowired
    ICardRightService cardRightService;
    
    @Autowired
    GunsProperties gunsProperties;

    /**
     * 跳转到会员卡首页
     */
    @Permission
    @RequestMapping("")
    public String index() {
    	log.info("测试日志");
        return PREFIX + "card.html";
    }

    /**
     * 跳转到添加会员卡
     */
    @Permission
    @RequestMapping("/card_add")
    public String cardAdd(Model model) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	
    	Map<String, Object> couponCon = new HashMap<String, Object>();
		couponCon.put("merchantId", merchant.getId());
		couponCon.put("invalid", true);
		
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("merchantId", merchant.getId());
    	
    	model.addAttribute("merchantName", merchant.getName());
    	List<Coupon> coupons  = couponService.selectByCondition(couponCon);
    	// 查询优惠群
    	model.addAttribute("coupons", coupons);
    	// 查询颜色值
    	model.addAttribute("colors", ColorType.values());
    	
    	model.addAttribute("grantType", CardGrantType.values());
    	
    	List<Card> cards = cardService.selectByCondition(condition);
    	// 查询会员卡
    	model.addAttribute("cards", cards);
    	
    	//model.addAttribute("grantType", GrantType.NO_RULE.getCode());
    	// 查询商户Logo
    	model.addAttribute("logo", merchant.getHeadImg());
        return PREFIX + "card_add.html";
    }

    /**
     * 跳转到修改会员卡
     */
    @Permission
    @RequestMapping("/card_update/{cardId}")
	public String cardUpdate(@PathVariable Long cardId, Model model) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		Map<String, Object> couponCon = new HashMap<String, Object>();
		couponCon.put("merchantId", merchant.getId());
		couponCon.put("invalid", true);
		
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("merchantId", merchant.getId());

		model.addAttribute("merchantName", merchant.getName());
		List<Coupon> coupons = couponService.selectByCondition(couponCon);
		// 查询优惠群
		model.addAttribute("coupons", coupons);
		// 查询颜色值
		model.addAttribute("colors", ColorType.values());

		model.addAttribute("grantType", CardGrantType.values());

		List<Card> cards = cardService.selectByCondition(condition);
		// 查询会员卡
		model.addAttribute("cards", cards);
		// 查询商户Logo
		model.addAttribute("logo", merchant.getHeadImg());

		Card card = cardService.selectById(cardId);
		if(StringUtils.isNotBlank(card.getColorCode())){
			model.addAttribute("colorType", ColorType.valueOf(card.getColorCode()));
		}else{
			model.addAttribute("colorType", ColorType.Color010);
		}
		model.addAttribute(card);

		Map<String, Object> con = new HashMap<String, Object>();
		con.put("cardId", card.getId());
		List<CardRight> cardRights = cardRightService.selectByCondition(con);
		List<CardRight> couponRights = new ArrayList<CardRight>();
		if (CollectionUtils.isNotEmpty(cardRights)) {
			for (CardRight cardRight : cardRights) {
				if (cardRight.getRightType().equals(RightType.DISCOUNT.getCode())) {
					model.addAttribute(RightType.DISCOUNT.getCode(), cardRight);
				} else if (cardRight.getRightType().equals(RightType.POINTS.getCode())) {
					model.addAttribute(RightType.POINTS.getCode(), cardRight);
				} else if (cardRight.getRightType().equals(RightType.COUPON.getCode())) {
					couponRights.add(cardRight);
				}
			}
		}
		model.addAttribute(RightType.COUPON.getCode(), couponRights);

		return PREFIX + "card_edit.html";
	}

    /**
     * 获取会员卡列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	Page<Card> page = new PageFactory<Card>().defaultPage();
    	List<Map<String, Object>> result = cardService.getCardList(page,condition,page.getOrderByField(), page.isAsc(),merchant.getId());
    	page.setRecords((List<Card>) new CardWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增会员卡
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Card card,String rights) {
		Long userId = Long.valueOf(ShiroKit.getUser().getId());
		Merchant merchant = merchantService.getMerchantUserId(userId);
		card.setMerchantId(merchant.getId());
		cardService.saveCard(card, rights);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除会员卡
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long cardId) {
        cardService.deleteById(cardId);
        return SUCCESS_TIP;
    }


    /**
     * 修改会员卡
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Card card,String rights) {
    	if (ToolUtil.isEmpty(card) || card.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	cardService.saveCard(card,rights);
        return super.SUCCESS_TIP;
    }
    
    /**
     * 验证会员卡名称是否存在
     * @return
     */
    @RequestMapping("/checkName")
    @Permission(Const.MERCHANT_NAME)
    @ResponseBody
    public Object checkName(Long id , String cardName){
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	return cardService.checkCardName(id, cardName,merchant.getId());
    }
    
    /**
     * 验证会员卡级别是否存在
     * @return
     */
    @RequestMapping("/checkLevel")
    @Permission(Const.MERCHANT_NAME)
    @ResponseBody
    public Object checkLevel(Long id , Integer cardLevel){
    	Long userId = Long.valueOf(ShiroKit.getUser().getId());
    	Merchant merchant = merchantService.getMerchantUserId(userId);
    	return cardService.checkCardLevel(id, cardLevel,merchant.getId());
    }

    /**
     * 会员卡详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(Long id, String cardName) {
       return null;
    }
}
