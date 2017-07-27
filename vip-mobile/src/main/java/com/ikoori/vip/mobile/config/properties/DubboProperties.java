package com.ikoori.vip.mobile.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * dubbo配置
 * </p>
 * <p>
 * 说明:这个类中包含了许多默认配置,若这些配置符合您的情况,您可以不用管,若不符合,建议不要修改本类,建议直接在"application.yml"中配置即可
 * </p>
 * 
 * @author fengshuonan
 * @date 2017-05-21 11:18
 */
@Component
@ConfigurationProperties(prefix = "dubbo")
public class DubboProperties {
	private String applicationName = "krvip.provider";
	private String registryAddress = "127.0.0.1:2181";
	private String registryProtocol = "zookeeper";
	private Integer protocolPort = 20880;
	private String monitorProtocol = "registry";

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getRegistryAddress() {
		return registryAddress;
	}

	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	public Integer getProtocolPort() {
		return protocolPort;
	}

	public void setProtocolPort(Integer protocolPort) {
		this.protocolPort = protocolPort;
	}

	public String getRegistryProtocol() {
		return registryProtocol;
	}

	public void setRegistryProtocol(String registryProtocol) {
		this.registryProtocol = registryProtocol;
	}

	public String getMonitorProtocol() {
		return monitorProtocol;
	}

	public void setMonitorProtocol(String monitorProtocol) {
		this.monitorProtocol = monitorProtocol;
	}

	
}
