package com.ikoori.vip.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 积分规则
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_point")
public class Point extends Model<Point> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 创建用户id
     */
	@TableField("create_user_id")
	private Long createUserId;
    /**
     * 店铺id
     */
	@TableField("store_id")
	private Long storeId;
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 规则名称
     */
	private String name;
    /**
     * 规则类型(0:关注微信;1:成功交易笔数;2:购物金额数)
     */
	@TableField("rule_type")
	private Integer ruleType;
    /**
     * 积分条件
     */
	@TableField("points_limit")
	private Integer pointsLimit;
    /**
     * 奖励分值
     */
	private Integer points;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 状态
     */
	@TableLogic
	private Integer status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getPointsLimit() {
		return pointsLimit;
	}

	public void setPointsLimit(Integer pointsLimit) {
		this.pointsLimit = pointsLimit;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Point{" +
			"id=" + id +
			", createUserId=" + createUserId +
			", storeId=" + storeId +
			", merchantId=" + merchantId +
			", name=" + name +
			", ruleType=" + ruleType +
			", pointsLimit=" + pointsLimit +
			", points=" + points +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
