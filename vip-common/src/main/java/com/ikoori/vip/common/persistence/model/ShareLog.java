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
 * 邀请日志
 * </p>
 *
 * @author chengxg
 * @since 2017-10-16
 */
@TableName("v_share_log")
public class ShareLog extends Model<ShareLog> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 分享人unionid
     */
	@TableField("share_unionid")
	private String shareUnionid;
    /**
     * 接受人unionid
     */
	@TableField("receive_unionid")
	private String receiveUnionid;
    /**
     * 分享状态0:未成功1:成功
     */
	@TableField("receive_status")
	private Boolean receiveStatus;
    /**
     * 接受人ip
     */
	@TableField("receive_ip")
	private String receiveIp;
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
     * 删除状态
     */
	@TableLogic
	private Boolean status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShareUnionid() {
		return shareUnionid;
	}

	public void setShareUnionid(String shareUnionid) {
		this.shareUnionid = shareUnionid;
	}

	public String getReceiveUnionid() {
		return receiveUnionid;
	}

	public void setReceiveUnionid(String receiveUnionid) {
		this.receiveUnionid = receiveUnionid;
	}

	public Boolean isReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(Boolean receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getReceiveIp() {
		return receiveIp;
	}

	public void setReceiveIp(String receiveIp) {
		this.receiveIp = receiveIp;
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
		return "ShareLog{" +
			"id=" + id +
			", shareUnionid=" + shareUnionid +
			", receiveUnionid=" + receiveUnionid +
			", receiveStatus=" + receiveStatus +
			", receiveIp=" + receiveIp +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
