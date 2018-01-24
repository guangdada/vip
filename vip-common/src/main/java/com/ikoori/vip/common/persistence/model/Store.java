package com.ikoori.vip.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import java.io.Serializable;

/**
 * <p>
 * 店铺
 * </p>
 *
 * @author chengxg
 * @since 2017-08-07
 */
@TableName("v_store")
public class Store extends Model<Store> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 店铺名称
     */
	private String name;
	
	/**
	 * 门店编号
	 */
	@TableField("store_no")
	private String storeNo;
	
	/**
	 * 店铺类型 0:线上 1:线下
	 */
	@TableField("store_type")
	private Integer storeType;
    /**
     * 店铺logo
     */
	private String logo;
    /**
     * 店铺余额
     */
	private BigDecimal balance;
    /**
     * 省
     */
	@TableField("province_id")
	private Long provinceId;
    /**
     * 市
     */
	@TableField("city_id")
	private Long cityId;
    /**
     * 区
     */
	@TableField("area_id")
	private Long areaId;
    /**
     * 详细地址
     */
	private String address;
    /**
     * 经度
     */
	private String longitude;
    /**
     * 纬度
     */
	private String latitude;
    /**
     * 营业开始时间
     */
	@TableField("open_time")
	private String openTime;
    /**
     * 营业结束时间
     */
	@TableField("close_time")
	private String closeTime;
    /**
     * 主营商品
     */
	@TableField("goods_type")
	private String goodsType;
    /**
     * 客服电话
     */
	@TableField("service_phone")
	private String servicePhone;
    /**
     * 官网地址
     */
	@TableField("website")
	private String website;
    /**
     * 店铺状态
     */
	private Integer state;
	
	/**
	 * 公众号二维码
	 */
	private String qrcode;
	
	/**
	 * 加盟热线
	 */
	private String jointel;
	
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
	
	@TableField("description")
	private String description;
    /**
     * 状态
     */
	@TableLogic
	private Integer status;
	
	@Version
	private Integer version;
	
	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	
	
	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getJointel() {
		return jointel;
	}

	public void setJointel(String jointel) {
		this.jointel = jointel;
	}

	@Override
	public String toString() {
		return "Store{" +
			"id=" + id +
			", merchantId=" + merchantId +
			", userId=" + userId +
			", name=" + name +
			", logo=" + logo +
			", balance=" + balance +
			", provinceId=" + provinceId +
			", cityId=" + cityId +
			", areaId=" + areaId +
			", address=" + address +
			", longitude=" + longitude +
			", latitude=" + latitude +
			", openTime=" + openTime +
			", closeTime=" + closeTime +
			", goodsType=" + goodsType +
			", servicePhone=" + servicePhone +
			", website=" + website +
			", state=" + state +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
