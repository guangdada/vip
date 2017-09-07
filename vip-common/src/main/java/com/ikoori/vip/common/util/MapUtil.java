package com.ikoori.vip.common.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	/**
	 * 计算传入的经纬度，半径内的范围
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            经度
	 * @param raidus
	 *            半径10km
	 */
	public static Map<String, Object> loadGeoSquare(double lat, double lon, long raidus) {
		Double latitude = lat;// 当前纬度
		Double longitude = lon;// 当前经度
		// 赤道周长24901英里 1609是转换成米的系数
		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("minLat", minLat);
		param.put("minLng", minLng);
		param.put("maxLat", maxLat);
		param.put("maxLng", maxLng);
		return param;
	}
}
