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
 * 商户公众号配置
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_wx_config")
public class WxConfig extends Model<WxConfig> {

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
     * appid
     */
	private String appid;
    /**
     * 微信支付商户号
     */
	@TableField("mch_id")
	private String mchId;
    /**
     * EncodingAESKey
     */
	@TableField("encodingAESKey")
	private String encodingAESKey;
    /**
     * API密钥
     */
	@TableField("api_key")
	private String apiKey;
    /**
     * Appsecret
     */
	private String secret;
    /**
     * 微信API票据
     */
	@TableField("wechat_ticket")
	private String wechatTicket;
    /**
     * 微信API令牌
     */
	@TableField("access_token")
	private String accessToken;
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

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String EncodingAESKey) {
		this.encodingAESKey = EncodingAESKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getWechatTicket() {
		return wechatTicket;
	}

	public void setWechatTicket(String wechatTicket) {
		this.wechatTicket = wechatTicket;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
		return "WxConfig{" +
			"id=" + id +
			", merchantId=" + merchantId +
			", appid=" + appid +
			", mchId=" + mchId +
			", EncodingAESKey=" + encodingAESKey +
			", apiKey=" + apiKey +
			", secret=" + secret +
			", wechatTicket=" + wechatTicket +
			", accessToken=" + accessToken +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
