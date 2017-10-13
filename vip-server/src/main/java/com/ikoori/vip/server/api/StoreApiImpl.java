package com.ikoori.vip.server.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ikoori.vip.api.service.StoreApi;
import com.ikoori.vip.common.constant.state.StoreType;
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
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	StoreDao storeDao;
	@Autowired
	PictureDao pictureDao;
	@Autowired
	GunsProperties gunsProperties;
	@Autowired
	private StorePhotoDao storePhotoDao;
	
	/**   
	 * <p>Title: loadStore</p>   
	 * <p>Description: 会员附近门店 </p>   
	 * @param lat 纬度
	 * @param lon 经度
	 * @return   
	 * @see com.ikoori.vip.api.service.StoreApi#loadStore(double, double)   
	 */  
	@Override
	public List<Map<String, Object>> loadStore(double lat, double lon) {
		log.info("进入loadStore");
		long raidus = gunsProperties.getRaidus() == null ? 10000 : gunsProperties.getRaidus(); // 半径10km
		Map<String, Object> param = MapUtil.loadGeoSquare(lat, lon, raidus);
		List<Map<String, Object>> store = storeDao.getStore(lat, lon, (Double) param.get("minLat"),
				(Double) param.get("minLng"), (Double) param.get("maxLat"), (Double) param.get("maxLng"));
		if (store == null) {
			log.info("store == null");
			return null;
		}
		log.info("结束loadStore");
		return store;
	}
	
	
	/**   
	 * <p>Title: getStoreDetail</p>   
	 * <p>Description: 会员门店详情</p>   
	 * @param storeId 门店id
	 * @return   
	 * @see com.ikoori.vip.api.service.StoreApi#getStoreDetail(java.lang.Long)   
	 */  
	@Override
	public JSONObject getStoreDetail(Long storeId) {
		log.info("进入getStoreDetail");
		Store store = storeDao.getStoreDetail(storeId);
		JSONObject obj = new JSONObject();
		List<Picture> pictures = storePhotoDao.selectStorePhoto(storeId);
		obj.put("id", store.getId());
		obj.put("name", store.getName());
		obj.put("address", store.getAddress());
		obj.put("latitude", store.getLatitude());
		obj.put("longitude", store.getLongitude());
		obj.put("servicePhone", store.getServicePhone());
		obj.put("openTime", store.getOpenTime());
		obj.put("closeTime", store.getCloseTime());
		obj.put("pictures", pictures);
		log.info("结束getStoreDetail");
		return obj;
	}


	/**   
	 * <p>Title: getStore</p>   
	 * <p>Description: 获取附近门店</p>   
	 * @param openId 
	 * @return   
	 * @see com.ikoori.vip.api.service.StoreApi#getStore(java.lang.String)   
	 */  
	@Override
	public List<Map<String, Object>> getStore(String openId) {
		log.info("进入getStore");
		List<Map<String, Object>> store = storeDao.getStoreByOpenId(openId,StoreType.offline.getCode());
		if (store == null) {
			log.info("store == null");
			return null;
		}
		log.info("结束getStore");
		return store;
	}
}
