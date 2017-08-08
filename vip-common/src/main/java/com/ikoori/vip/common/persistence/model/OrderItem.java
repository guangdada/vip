package com.ikoori.vip.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单明细
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_order_item")
public class OrderItem extends Model<OrderItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单id
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 订单号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 数量
     */
	private Integer quantity;
    /**
     * 成本价(单位:分)
     */
	@TableField("original_price")
	private Integer originalPrice;
    /**
     * 优惠金额(单位:分)
     */
	private Integer discount;
    /**
     * 产品名称
     */
	@TableField("product_name")
	private String productName;
    /**
     * 零售价格(单位:分)
     */
	@TableField("case_price")
	private Integer casePrice;
    /**
     * 商品编码(P170714100848376)
     */
	@TableField("sku_no")
	private String skuNo;
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
	private Boolean status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getCasePrice() {
		return casePrice;
	}

	public void setCasePrice(Integer casePrice) {
		this.casePrice = casePrice;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
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

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OrderItem{" +
			"id=" + id +
			", orderId=" + orderId +
			", orderNo=" + orderNo +
			", quantity=" + quantity +
			", originalPrice=" + originalPrice +
			", discount=" + discount +
			", productName=" + productName +
			", casePrice=" + casePrice +
			", skuNo=" + skuNo +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
