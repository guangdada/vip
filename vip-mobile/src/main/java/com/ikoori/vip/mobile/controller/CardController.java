package com.ikoori.vip.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ikoori.vip.mobile.config.DubboConsumer;

@Controller
@RequestMapping("/card")
public class CardController {
	 @Autowired
	 DubboConsumer consumer;
}
