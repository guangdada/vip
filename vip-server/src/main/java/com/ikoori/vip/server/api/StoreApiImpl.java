package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.StoreApi;
import com.ikoori.vip.common.persistence.model.Picture;
import com.ikoori.vip.common.persistence.model.Store;
import com.ikoori.vip.common.util.MapUtil;
import com.ikoori.vip.server.config.properties.GunsProperties;
import com.ikoori.vip.server.modular.biz.dao.PictureDao;
import com.ikoori.vip.server.modular.biz.dao.StoreDao;
import com.ikoori.vip.server.modular.biz.dao.StorePhotoDao;

/**  
* @ClassName: StoreApiImpl  
* @Description: 附近门店
* @author :huanglin 
* @date 2017年9月8日  
*    
*/  
@Service
public class StoreApiImpl implements StoreApi {
	@Autowired
	StoreDao storeDao;
	@Autowired
	PictureDao pictureDao;
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	private StorePhotoDao storePhotoDao;
	@Override
	public List<Map<String, Object>> loadStore(double lat, double lon) {
		long raidus = gunsProperties.getRaidus() == null ? 10000 : gunsProperties.getRaidus(); // 半径10km
		Map<String, Object> param = MapUtil.loadGeoSquare(lat, lon, raidus);
		List<Map<String, Object>> store= storeDao.getStore(lat, lon, (Double)param.get("minLat"),(Double)param.get("minLng"),(Double)param.get("maxLat"),(Double)param.get("maxLng"));
	    if(store==null){
	    	return null;
	    }
		return store;
	}
	@Override
	public JSONObject getStoreDetail(Long storeId) {
		Store store=storeDao.getStoreDetail(storeId);
		JSONObject obj = new JSONObject();
		List<Picture> pictures=storePhotoDao.selectStorePhoto(storeId);
		obj.put("id", store.getId());
		obj.put("name", store.getName());
		obj.put("address",store.getAddress());
		obj.put("latitude", store.getLatitude());
		obj.put("longitude", store.getLongitude());
		obj.put("servicePhone", store.getServicePhone());
		obj.put("openTime", store.getOpenTime());
		obj.put("closeTime", store.getCloseTime());
		obj.put("pictures",pictures);
		return obj;
	}
}
