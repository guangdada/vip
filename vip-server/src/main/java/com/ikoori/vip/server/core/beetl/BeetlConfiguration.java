package com.ikoori.vip.server.core.beetl;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.ikoori.vip.server.core.util.ToolUtil;


public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

	@Override
	public void initOther() {

		groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
		groupTemplate.registerFunctionPackage("tool", new ToolUtil());

	}

}
