package com.ikoori.vip.mobile.modular.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ikoori.vip.mobile.config.Consumer;

@Controller
public class MemberController {
	//@Reference(version = "1.0.0")
	//private MemberService memberService;
	 @Autowired
	 Consumer consumer;


	@RequestMapping("/login1")
	@ResponseBody
	public String run() {
		//memberService.test();
		consumer.personConsumer().get().test();
		return "";
	}
}
