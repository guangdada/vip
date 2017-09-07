package com.ikoori.vip.api.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface StoreApi {
	public List<Map<String, Object>> loadStore(double lat, double lon);
	public JSONObject getStoreDetail(Long storeId);
	public List<Map<String,Object>>getStorePicture(Long storeId);
}
