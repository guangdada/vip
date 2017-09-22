package com.ikoori.vip.server.modular.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ikoori.vip.common.constant.state.CouponType;
import com.ikoori.vip.common.constant.state.CouponUseState;
import com.ikoori.vip.common.constant.state.PointTradeType;
import com.ikoori.vip.common.constant.state.PointType;
import com.ikoori.vip.common.dto.CouponPayDo;
import com.ikoori.vip.common.dto.OrderItemPayDo;
import com.ikoori.vip.common.dto.OrderPayDo;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.CouponFetchMapper;
import com.ikoori.vip.common.persistence.dao.CouponMapper;
import com.ikoori.vip.common.persistence.dao.CouponTradeMapper;
import com.ikoori.vip.common.persistence.dao.MemberCardMapper;
import com.ikoori.vip.common.persistence.dao.MemberMapper;
import com.ikoori.vip.common.persistence.dao.OrderItemMapper;
import com.ikoori.vip.common.persistence.dao.OrderMapper;
import com.ikoori.vip.common.persistence.dao.PointTradeMapper;
import com.ikoori.vip.common.persistence.model.Card;
import com.ikoori.vip.common.persistence.model.Coupon;
import com.ikoori.vip.common.persistence.model.CouponFetch;
import com.ikoori.vip.common.persistence.model.CouponTrade;
import com.ikoori.vip.common.persistence.model.Member;
import com.ikoori.vip.common.persistence.model.Order;
import com.ikoori.vip.common.persistence.model.OrderItem;
import com.ikoori.vip.common.persistence.model.Point;
import com.ikoori.vip.common.persistence.model.PointTrade;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.util.DateUtil;
import com.ikoori.vip.common.util.RandomUtil;
import com.ikoori.vip.server.modular.biz.dao.CardDao;
import com.ikoori.vip.server.modular.biz.dao.CouponDao;
import com.ikoori.vip.server.modular.biz.dao.CouponFetchDao;
import com.ikoori.vip.server.modular.biz.dao.MemberCardDao;
import com.ikoori.vip.server.modular.biz.dao.MemberDao;
import com.ikoori.vip.server.modular.biz.dao.OrderDao;
import com.ikoori.vip.server.modular.biz.dao.PointDao;
import com.ikoori.vip.server.modular.biz.dao.StoreDao;
import com.ikoori.vip.server.modular.biz.service.IMemberService;
import com.ikoori.vip.server.modular.biz.service.IOrderService;
import com.ikoori.vip.server.modular.biz.service.IPointTradeService;

/**
 * 订单Dao
 *
 * @author chengxg
 * @Date 2017-08-26 17:44:40
 */
@Service
public class OrderServiceImpl implements IOrderService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderItemMapper orderItemMapper;
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	CouponFetchMapper couponFetchMapper;
	@Autowired
	MemberCardMapper memberCardMapper;
	@Autowired
	MemberCardDao memberCardDao;
	@Autowired
	PointTradeMapper pointTradeMapper;
	@Autowired
	CouponFetchDao couponFetchDao;
	@Autowired
	CouponTradeMapper couponTradeMapper;
	@Autowired
	CouponMapper couponMapper;
	@Autowired
	StoreDao storeDao;
	@Autowired
	PointDao pointDao;
	@Autowired
	CardDao cardDao;
	@Autowired
	CouponDao couponDao;
	@Autowired
	IMemberService memberService;
	@Autowired
	IPointTradeService pointTradeService;
	
	@Override
	public Integer deleteById(Long id) {
		return orderMapper.deleteById(id);
	}

	@Override
	public Integer updateById(Order order) {
		return orderMapper.updateById(order);
	}

	@Override
	public Order selectById(Long id) {
		return orderMapper.selectById(id);
	}

	@Override
	public Integer insert(Order order) {
		return orderMapper.insert(order);
	}
	
	@Override
	public List<Map<String, Object>> getOrderList(Page<Map<String, Object>> page, String memName, String orderByField,
			boolean isAsc, Long merchantId, Long storeId, String mobile, Long orderSource, String orderNo) {
		return orderDao.getOrderList(page, memName, orderByField, isAsc, merchantId, storeId, mobile, orderSource,
				orderNo);
	}
	
	/**
	 * 保存门店订单数据
	 * 扣减使用的优惠券
	 * 扣减 使用的积分
	 * 根据积分规则累加积分
	 * 根据会员卡规则升级会员卡
	 */
	@Transactional(readOnly = false)
	public void saveOrder(OrderPayDo orderPayDo) throws Exception{
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>保存门店订单数据>>>>>>>>>>>>>>>>>>>>>>>>");
		Store store = storeDao.selectByStoreNo(orderPayDo.getStoreNo());
		if(store == null){
			log.error("没有找到店铺信息,店铺编号：" + orderPayDo.getStoreNo());
			throw new BussinessException(500,"没有找到店铺信息");
		}
		// 判断积分是否足够
		Member member = memberDao.selectByMobile(orderPayDo.getMobile());
		if(member == null){
			log.error("没有找到该会员信息,手机号：" + orderPayDo.getMobile());
			throw new BussinessException(500,"没有找到该会员信息");
		}
		
		log.info("保存订单");
		// 保存order
		Order order = new Order();
		order.setPayOrderNo(orderPayDo.getOrderNo());
		order.setOrderNo(RandomUtil.generateOrderNo());
		order.setStoreId(store.getId());
		order.setBalanceDue(orderPayDo.getBalanceDue());
		order.setPayment(orderPayDo.getPayment());
		order.setDiscount(orderPayDo.getDiscount());
		//order.setDiscountInfo(discountInfo);
		orderMapper.insert(order);
		
		log.info("保存订单明细");
		// 保存orderItem
		List<OrderItemPayDo> itemPayDos = orderPayDo.getOrderItems();
		if(itemPayDos!=null){
			for(OrderItemPayDo itemPayDo : itemPayDos){
				OrderItem orderItem = new OrderItem();
				orderItem.setGoodsName(itemPayDo.getGoodsName());
				orderItem.setQuantity(itemPayDo.getQuantity());
				orderItem.setGoodsNo(itemPayDo.getGoodsNo());
				orderItem.setCasePrice(itemPayDo.getCasePrice());
				orderItem.setModel(itemPayDo.getModel());
				orderItem.setOriginalPrice(itemPayDo.getOriginalPrice());
				orderItemMapper.insert(orderItem);
			}
		}
		
		log.info("扣减使用的积分:" + orderPayDo.getPoint());
		// 扣减使用的积分
		if (orderPayDo.getPoint() != null && (orderPayDo.getPoint() > 0)) {
			if(member.getPoints() < orderPayDo.getPoint()){
				log.error("积分不足，剩余积分"+member.getPoints());
				throw new BussinessException(500,"积分不足，剩余积分"+member.getPoints());
			}
			//member.setPoints(member.getPoints() - orderPayDo.getPoint());
			//member.setVersion(member.getVersion());
			//int count = memberMapper.updateById(member);
			//修改会员积分
			int count = memberDao.updatePoint(member.getId(), -orderPayDo.getPoint());
			if(count == 0 ){
				log.error("扣减积分失败");
				throw new BussinessException(500,"扣减积分失败");
			}
			
			log.error("生成积分使用记录");
			pointTradeService.savePointTrade(false, PointTradeType.PAY_ORDER.getCode(), -orderPayDo.getPoint(),
					member.getId(), null, member.getMerchantId(), store.getId(), "");
			// 生成积分使用记录
			/*PointTrade pointTrade = new PointTrade();
			pointTrade.setMemberId(member.getId());
			pointTrade.setOrderId(order.getId());
			pointTrade.setPoint(orderPayDo.getPoint());
			pointTrade.setInOut(false);
			pointTrade.setTradeType(PointTradeType.PAY_ORDER.getCode());
			pointTrade.setMerchantId(member.getMerchantId());
			pointTrade.setStoreId(store.getId());
			pointTradeMapper.insert(pointTrade);*/
		}
		
		log.info("扣减使用现金券");
		// 扣减使用现金券
		if(orderPayDo.getCoupons() != null){
			for(CouponPayDo couPayDo : orderPayDo.getCoupons()){
				Integer usedValue =couPayDo.getUsedValue();
				String verifyCode = couPayDo.getVerifyCode();
				String msg = "优惠券:"+verifyCode;
				// 根据券码获得优惠券
				log.info("根据券码获得优惠券：" + verifyCode);
				//CouponFetch cf = couponFetchDao.selectByVerifyCodeJoinCoupon(verifyCode);
				CouponFetch cf = couponFetchDao.selectByVerifyCode(verifyCode);
				if(cf == null){
					throw new BussinessException(500,msg+",没有找到");
				}
				Coupon coupon  = couponMapper.selectById(cf.getCouponId());
				if(coupon.isIsAtLeast()){
					if(coupon.getOriginAtLeast() < orderPayDo.getBalanceDue()){
						throw new BussinessException(500,msg+",购物满" + coupon.getAtLeast() + "元才能使用");
					}
				}
				// 代金券判断是否已经使用，改成已使用
				if(!cf.getIsInvalid()){
					throw new BussinessException(500,msg+",已经失效");
				}
				if(cf.getIsUsed().intValue() == CouponUseState.USED.getCode()){
					throw new BussinessException(500,msg+",已经被使用");
				}
				if(!cf.getMemberId().toString().equals(member.getId().toString())){
					throw new BussinessException(500,msg + ",不是本人的");
				}
				if(usedValue > cf.getValue()){
					throw new BussinessException(500,msg +"面值为"+cf.getValue()/100+"元,余额不足");
				}
				
				/*只精确到天*/
				/*String validTime = DateUtil.format(cf.getValidTime(), DateUtil.dateFormat);
				String expireTime = DateUtil.format(cf.getExpireTime(), DateUtil.dateFormat);
				// 是否已生效
				if(!DateUtil.compareDate(DateUtil.getDay(),validTime)){
					throw new BussinessException(500,msg + ",还未生效");
				}
				// 是否已过期
				if(!DateUtil.compareDate(expireTime,DateUtil.getDay())){
					throw new BussinessException(500,msg + ",已经过期");
				}*/
				
				/*精确到秒*/
				Date now = new Date();
				// 是否已生效
				if(!DateUtil.compareWithLongTime(now,cf.getValidTime())){
					throw new BussinessException(500,msg + ",还未生效");
				}
				// 是否已过期
				if(!DateUtil.compareWithLongTime(cf.getExpireTime(),now)){
					throw new BussinessException(500,msg + ",已经过期");
				}
				
				// 现金券扣减使用金额、没使用完改成部分使用，判断余额是否足够
				if(coupon.getType().intValue() == CouponType.XJQ.getCode()){
					if(usedValue > cf.getAvailableValue()){
						throw new BussinessException(500,msg +"可用金额为"+cf.getAvailableValue()/100+"元,余额不足");
					}
					// 扣减现金券使用金额
					cf.setUsedValue(cf.getUsedValue() + usedValue);
					cf.setAvailableValue(cf.getAvailableValue() - usedValue);
					//使用完了
					if(cf.getAvailableValue() == 0){
						cf.setIsUsed(CouponUseState.USED.getCode());
						cf.setIsInvalid(false);
					}else{
						cf.setIsUsed(CouponUseState.PART_USED.getCode());
					}
				}else {
					// 优惠券修改为已使用状态
					cf.setIsUsed(CouponUseState.USED.getCode());
					cf.setIsInvalid(false);
				}
				
				cf.setVersion(cf.getVersion());
				int count = couponFetchMapper.updateById(cf);
				if(count == 0){
					throw new BussinessException(500,"优惠券使用失败");
				}
				
				log.info("生成优惠券使用记录");
				// 生成优惠券使用记录
				CouponTrade couponTrade = new CouponTrade();
				couponTrade.setCouponId(coupon.getId());
				couponTrade.setMemberId(member.getId());
				couponTrade.setStoreId(store.getId());
				couponTrade.setMerchantId(member.getMerchantId());
				couponTrade.setUsedValue(usedValue);
				couponTrade.setUsedOrderNo(order.getOrderNo());
				couponTrade.setUsedOrderId(order.getId());
				couponTrade.setVerifyCode(verifyCode);
				//couponTrade.setWxUserId(member.getWxUserId());
				couponTradeMapper.insert(couponTrade);
				
				// 更新优惠券使用次数
				//coupon.setVersion(coupon.getVersion());
				//coupon.setUseCount(coupon.getUseCount() + 1);
				//couponMapper.updateById(coupon);
				log.info("更新优惠券使用次数");
				couponDao.updateUseCount(coupon.getId(), 1);
			}
		}
		// 根据积分规则返还积分
		log.info("根据积分规则返还积分");
		List<Point> points = pointDao.selectByMerchantId(member.getMerchantId());
		if(points != null){
			for(Point point : points){
				// 生成积分使用记录
				PointTrade pointTrade = new PointTrade();
				int p = point.getPoints();
				if(point.getRuleType().intValue() == PointType.PAY_MONEY.getCode()){
					// 求当前订单金额和限制金额的除数
					int due = orderPayDo.getBalanceDue() / point.getPointsLimit();
					// 比如每购买10元送1积分，当前订单金额为100的话，得到的除数为10*奖励分值，即为要返还的积分数
					pointTrade.setPoint(p * due);
					pointTrade.setTradeType(PointTradeType.ORDER_MONEY.getCode());
				}
				if(point.getRuleType().intValue() == PointType.PAY_ORDER.getCode()){
					// 查询上一次按购买数量获得积分的记录id
					Long lastOrderId = getLastOrderId(member.getId());
					// 获得之后的购买次数+1(当前算一次)
					int orderCount = getMemOrderCount(member.getId(),lastOrderId);
					// 判断累加后的数量是否大于等于限制条件
					if((orderCount + 1) >= point.getPointsLimit()){
						pointTrade.setPoint(p);
						pointTrade.setTradeType(PointTradeType.ORDER_COUNT.getCode());
					}
				}
				if(pointTrade.getPoint() > 0){
					log.info("返还积分：" + pointTrade.getPoint());
					pointTrade.setPointId(point.getId());
					pointTrade.setMemberId(member.getId());
					pointTrade.setOrderId(order.getId());
					pointTrade.setInOut(true);
					pointTrade.setMerchantId(member.getMerchantId());
					pointTrade.setStoreId(store.getId());
					pointTradeMapper.insert(pointTrade);
					
					//Member memberdb = memberMapper.selectById(member.getId());
					//memberdb.setPoints(member.getPoints() + pointTrade.getPoint());
					//memberdb.setVersion(member.getVersion());
					//int count = memberMapper.updateById(memberdb);
					int count = memberDao.updatePoint(member.getId(), pointTrade.getPoint());
					//修改会员积分
					log.info("累加会员积分");
					if(count == 0 ){
						throw new BussinessException(500,"累加积分失败");
					}
				}
			}
		}
		
		// 判断是否满足会员卡升级条件
		// 查询所有的"按规则"类别的会员卡，按等级升序排序后，逐个判断是否满足升级到改卡
		log.info("判断是否满足会员卡升级条件");
		List<Card> cards = cardDao.selectByMerchantId(member.getMerchantId());
		if (CollectionUtils.isNotEmpty(cards)) {
			// 获得累计订单数量
			int orderCount = getMemOrderCount(member.getId(), 0L);
			// 获得累计交易金额
			Integer totalPayment = getMemPaymemt(member.getId(), 0L);
			// 获得累计积分
			Member mem = memberMapper.selectById(member.getId());
			Integer point = mem.getPoints();
			for (Card card : cards) {
				Integer tradeLimit = card.getTradeLimit();
				Integer pointsLimit = card.getPointsLimit();
				Integer amountLimit = card.getAmountLimit();
				boolean upgrade = false;
				if (tradeLimit != null && orderCount >= tradeLimit) {
					upgrade = true;
				} else if (pointsLimit != null && point >= pointsLimit) {
					upgrade = true;
				} else if (amountLimit != null && totalPayment >= (amountLimit * 100)) {
					upgrade = true;
				}
				// 满足升级条件，升级到该会员卡
				if (upgrade) {
					log.info("升级会员：" + mem.getId() + "到会员卡" + card.getId());
					/*MemberCard mc = getMemberCard(mem.getId(), card.getId());
					if (mc != null) {
						log.info("已经有该级别会员卡");
						// 已经有该级别会员卡
						mc.setIsDefault(true);
						memberCardMapper.updateById(mc);
					} else {
						log.info("没有该级别会员卡,发送一张");
						// 没有该级别会员卡
						MemberCard memCard = new MemberCard();
						memCard.setCardId(card.getId());
						memCard.setCardNumber(RandomUtil.generateCardNum(card.getCardNumberPrefix()));
						memCard.setMemberId(mem.getId());
						memCard.setMerchantId(mem.getMerchantId());
						memCard.setState(MemCardState.USED.getCode());
						memCard.setIsDefault(true);
						memberCardMapper.insert(memCard);
					}
					// 修改会员的默认会员卡
					log.info("修改会员的默认会员卡");
					updateDefaultCard(mem.getId(), card.getId());*/
					memberService.upgradeMemberCard(member, card);
					// 满足最高等级会员卡升级条件则退出
					break;
				}
			}
		}
		
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<保存门店订单数据<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
	
	/**
	 * 查询会员上一次按购买数量获得积分的记录id
	 * @param memberId
	 * @return
	 */
	public Long getLastOrderId(Long memberId){
		Wrapper<PointTrade> w = new EntityWrapper<PointTrade>();
		w.eq("member_id", memberId).eq("trade_type", PointTradeType.ORDER_COUNT.getCode()).orderBy("id", false);
		w.last(" limit 1");
		List<PointTrade> pts = pointTradeMapper.selectList(w);
		if(CollectionUtils.isNotEmpty(pts)){
			return pts.get(0).getId();
		}
		return 0L;
	}
	
	/**
	 * 获得大于orderId,之后的订单数量
	 * @param memberId
	 * @param lastOrderId
	 * @return
	 */
	public int getMemOrderCount(Long memberId,Long lastOrderId){
		Wrapper<Order> w = new EntityWrapper<Order>();
		w.eq("member_id", memberId).eq("status", 1).gt("id", lastOrderId);
		return orderMapper.selectCount(w);
	}
	
	/**
	 * 获得大于orderId,之后的交易金额
	 * @param memberId
	 * @param lastOrderId
	 * @return
	 */
	public Integer getMemPaymemt(Long memberId,Long lastOrderId){
		Wrapper<Order> w = new EntityWrapper<Order>();
		w.setSqlSelect("sum(payment)").eq("member_id", memberId).gt("id", lastOrderId);
		return orderMapper.selectCount(w);
	}
}
