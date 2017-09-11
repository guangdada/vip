package com.ikoori.vip.server.modular.system.controller;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.common.annotion.Permission;
import com.ikoori.vip.common.constant.Const;
import com.ikoori.vip.common.constant.state.PicType;
import com.ikoori.vip.common.exception.BizExceptionEnum;
import com.ikoori.vip.common.exception.BussinessException;
import com.ikoori.vip.common.persistence.dao.PictureMapper;
import com.ikoori.vip.common.persistence.model.Merchant;
import com.ikoori.vip.common.persistence.model.Picture;
import com.ikoori.vip.server.common.controller.BaseController;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.core.shiro.ShiroKit;
import com.ikoori.vip.server.modular.biz.service.IMerchantService;

/**
 * 问价上传控制器
 *
 * @author chengxg
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
	@Resource
	private GunsProperties gunsProperties;
	@Resource
	private PictureMapper pictureMapper;
	@Resource
	private IMerchantService merchantService;

	/**
	 * 上传LOGO图片(上传到项目的webapp/static/img)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/logo")
	@Permission({Const.ADMIN_NAME,Const.MERCHANT_NAME})
	@ResponseBody
	public JSONObject logo(@RequestPart("file") MultipartFile picture) {
		return upload(picture,PicType.LOGO);
	}
	
	/**
	 * 上传门店图片(上传到项目的webapp/static/img)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/storePic")
	@Permission({Const.ADMIN_NAME,Const.MERCHANT_NAME})
	@ResponseBody
	public JSONObject storePic(@RequestPart("file") MultipartFile picture) {
		return upload(picture,PicType.STORE);
	}
	
	/**
	 * 上传会员卡图片(上传到项目的webapp/static/img)
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/card")
	@Permission({Const.ADMIN_NAME,Const.MERCHANT_NAME})
	@ResponseBody
	public JSONObject card(@RequestPart("file") MultipartFile picture) {
		return upload(picture,PicType.CARD);
	}

	private JSONObject upload(MultipartFile picture,PicType picType) {
		JSONObject obj = new JSONObject();
		String pictureName = UUID.randomUUID().toString() + ".jpg";
		try {
			Long userId = Long.valueOf(ShiroKit.getUser().getId());
	    	
			String fileSavePath = gunsProperties.getFileUploadPath() + pictureName;
			picture.transferTo(new File(fileSavePath));

			Picture pic = new Picture();
			if(ShiroKit.hasRole(Const.MERCHANT_NAME)){
				Merchant merchant = merchantService.getMerchantUserId(userId);
				pic.setMerchantId(merchant.getId());
			}
			pic.setPictypeId(picType.getCode());
			pic.setRealName(picture.getOriginalFilename());
			pic.setAbsUrl(gunsProperties.getImageUrl()+ "/" +pictureName);
			pic.setName(pictureName);
			pictureMapper.insert(pic);
			obj.put("pictureName", pic.getAbsUrl());
			obj.put("pictureId", pic.getId());
		} catch (Exception e) {
			throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
		}
		return obj;
	}
}
