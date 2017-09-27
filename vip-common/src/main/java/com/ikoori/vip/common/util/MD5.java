package com.ikoori.vip.common.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;

public class MD5 {   
	
	private static Log logger = LogFactory.getLog(MD5.class);

	
	public static String getMD5(String pwd) {
		byte[] source =null;
		try {
			source=pwd.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String s = null;   
		char hexDigits[] = {       // �������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�   
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};    
		try  
		{   
			java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );   
			md.update( source );   
			byte tmp[] = md.digest();          // MD5 �ļ�������һ�� 128 λ�ĳ�����   
	                                                // ���ֽڱ�ʾ���� 16 ���ֽ�   
			char str[] = new char[16 * 2];   // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ�   
	                                                 // ���Ա�ʾ�� 16 ������Ҫ 32 ���ַ�   
			int k = 0;                                // ��ʾת������ж�Ӧ���ַ�λ��   
			for (int i = 0; i < 16; i++) {          // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�   
	                                                 // ת���� 16 �����ַ��ת��   
			byte byte0 = tmp[i];                 // ȡ�� i ���ֽ�   
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // ȡ�ֽ��и� 4 λ������ת��,    
	                                                             // >>> Ϊ�߼����ƣ������λһ������   
			str[k++] = hexDigits[byte0 & 0xf];            // ȡ�ֽ��е� 4 λ������ת��   
			}    
			s = new String(str);                                 // ����Ľ��ת��Ϊ�ַ�   
	  
		}catch( Exception e ) {   
		   e.printStackTrace();   
		}   
		return s;   
	}   
	
	
	
	
	public static String signTopRequestNew(Map<String, String> params,
			String secret, boolean isHmac) {
		// ��һ�����������Ƿ��Ѿ�����
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);

		// �ڶ����������в�����Ͳ���ֵ����һ��
		StringBuilder query = new StringBuilder();
		if (!isHmac) {
			query.append(secret);
		}
		for (String key : keys) {
			String value = params.get(key);
			if (null != key && null != value) {
				query.append(key).append(value);
			}
		}

		// ����ʹ��MD5/HMAC����
		byte[] bytes = null;
		if (isHmac) {
			try {
				bytes = encryptHMAC(query.toString().getBytes(), secret);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			query.append(secret);
			try {
				bytes = encryptMD5(query.toString());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("encryptMD5 exception");
			}
		}

		// ���Ĳ����Ѷ�����ת��Ϊ��д��ʮ�����
		return byte2hex(bytes);
	}
	
	  public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
	        byte[] bkey = new Base64().decode(key);
	        SecretKey secretKey = new SecretKeySpec(bkey, "HmacMD5");
	        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
	        mac.init(secretKey);
	        return mac.doFinal(data);
	    }


	public static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes("UTF-8"));
		} catch (GeneralSecurityException e) {
			logger.error(e);
		}
		return bytes;
	}
	
	public static String byte2hex(byte[] b) { 
		StringBuffer hs = new StringBuffer(); 
		String stmp = "";
		for (int n = 0; n < b.length; n++) {   
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); 
			if (stmp.length() == 1)   
				hs.append("0").append(stmp); 
			else   
				hs.append(stmp);
		
		}
		return hs.toString().toUpperCase();
	}
	
	public static void main(String[] args) {
		try {t();
			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	static Map<String,String> getTI(String par){ 
		Map<String,String> maps = new HashMap<String,String>();
		try{ 
			par=URLDecoder.decode(par,"GBK");
			System.out.println(par); 
			 Base64 base = new Base64();
			 System.out.println(new String(base.decode(par.getBytes("utf-8")),"utf-8"));
			 String ret = new String(base.decode(par.getBytes("utf-8")),"GBK");
			//String ret = new String(new Base64().decodeBuffer(new String(par.getBytes("GB2312"),"GB2312")),"GB2312"); 
			 System.out.println(ret);
			String m_[]= ret.split("&");
			for(int i=0;i<m_.length;i++){
				String[] d_=m_[i].split("=");
				maps.put(d_[0], d_[1]);
			}
			return maps;
		}catch(Exception e){
			System.out.println("");
		}
		return null;
	}
	public static void t() {
		String sql="OezXcEiiBSKSxW0eoylIeP0vrZKNUFaiKCV7NbFjnJu6goFepR6kzZc5NTN--2I-fHckWCS03u4Zs8uy-xtwLp0gh53Zd_Lp3gyFGr0Yh_QucXrs8PsCxFBtFyPyWhmyGHtLK6BcnKXkRVRPmBq0oA";
		getTI(sql);
		BASE64Decoder dc = new BASE64Decoder();
		try {
			byte [] bt =dc.decodeBuffer(sql);
			System.out.println(new String(bt,"gbk"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}