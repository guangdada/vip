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
 * 图片
 * </p>
 *
 * @author chengxg
 * @since 2017-08-23
 */
@TableName("v_picture")
public class Picture extends Model<Picture> {

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
     * 店铺id
     */
	@TableField("store_id")
	private Long storeId;
    /**
     * 名称
     */
	private String name;
    /**
     * 真实名称
     */
	@TableField("real_name")
	private String realName;
    /**
     * 图片类型
     */
	@TableField("pictype_id")
	private Integer pictypeId;
    /**
     * 相对路径
     */
	private String url;
    /**
     * 绝对路径
     */
	@TableField("abs_url")
	private String absUrl;
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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getPictypeId() {
		return pictypeId;
	}

	public void setPictypeId(Integer pictypeId) {
		this.pictypeId = pictypeId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAbsUrl() {
		return absUrl;
	}

	public void setAbsUrl(String absUrl) {
		this.absUrl = absUrl;
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
		return "Picture{" +
			"id=" + id +
			", merchantId=" + merchantId +
			", storeId=" + storeId +
			", name=" + name +
			", realName=" + realName +
			", pictypeId=" + pictypeId +
			", url=" + url +
			", absUrl=" + absUrl +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
