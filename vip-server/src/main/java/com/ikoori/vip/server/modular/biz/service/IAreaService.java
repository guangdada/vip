package com.ikoori.vip.server.modular.biz.service;

import java.util.List;

import com.ikoori.vip.common.persistence.model.Area;

/**
 * 区域Service
 *
 * @author chengxg
 * @Date 2017-08-02 12:31:42
 */
public interface IAreaService {
	public List<Area> searchRoot();
	public List<Area> searchNext(Long parentId);
}
