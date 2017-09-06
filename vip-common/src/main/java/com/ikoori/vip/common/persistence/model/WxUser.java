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
 * 微信用户
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_wx_user")
public class WxUser extends Model<WxUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 是否关注公众号
     */
	@TableField("is_subscribe")
	private Boolean isSubscribe;
    /**
     * openid
     */
	private String openid;
    /**
     * 昵称
     */
	private String nickname;
    /**
     * 性别
     */
	private Integer sex;
    /**
     * 城市
     */
	private String city;
    /**
     * 国家
     */
	private String country;
    /**
     * 省份
     */
	private String province;
    /**
     * unionId
     */
	private String unionid;
    /**
     * 用户语言
     */
	private String language;
    /**
     * 头像
     */
	private String headimgurl;
    /**
     * 关注时间
     */
	@TableField("subscribe_time")
	private Date subscribeTime;
    /**
     * 备注
     */
	private String remark;
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

	public Boolean isIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return "WxUser{" +
			"id=" + id +
			", isSubscribe=" + isSubscribe +
			", openid=" + openid +
			", nickname=" + nickname +
			", sex=" + sex +
			", city=" + city +
			", country=" + country +
			", province=" + province +
			", unionId=" + unionid +
			", language=" + language +
			", headimgurl=" + headimgurl +
			", subscribeTime=" + subscribeTime +
			", remark=" + remark +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
