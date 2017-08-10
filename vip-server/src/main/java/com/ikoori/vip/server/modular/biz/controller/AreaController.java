package com.ikoori.vip.server.modular.biz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ikoori.vip.common.persistence.model.Area;
import com.ikoori.vip.server.modular.biz.service.IAreaService;

@Controller
@RequestMapping("/area")
public class AreaController {
	@Autowired
	private IAreaService areaService;
	
	@RequestMapping(value = "listSelect", method = { RequestMethod.GET })
	@ResponseBody
	public List<Area> listSelect() {
		List<Area> lists = areaService.searchRoot();
		return lists;
	}

	@RequestMapping(value = "nextSelect", method = { RequestMethod.GET })
	@ResponseBody
	public List<Area> nextSelect(Long parentId) {
		List<Area> lists = areaService.searchNext(parentId);
		return lists;
	}

	/*@RequestMapping(value = "listSelect", method = { RequestMethod.GET })
	public void listSelect(ServletRequest request, ServletResponse response) {
		List<Area> lists = areaService.searchRoot();
		PrintWriter pw = null;
		response.setContentType("textml;charset=UTF-8");
		try {
			JSONArray array = new JSONArray();
			for (Area item : lists) {
				JSONObject json = new JSONObject();
				json.put("id", item.getId());
				json.put("name", item.getName());
				array.add(json);
			}
			pw = response.getWriter();
			pw.print(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}

	@RequestMapping(value = "nextSelect", method = { RequestMethod.GET })
	public void nextSelect(ServletRequest request, ServletResponse response, Long parentId) {
		List<Area> lists = areaService.searchNext(parentId);

		PrintWriter pw = null;
		response.setContentType("textml;charset=UTF-8");
		try {
			JSONArray array = new JSONArray();
			for (Area item : lists) {
				JSONObject json = new JSONObject();
				json.put("id", item.getId());
				json.put("name", item.getName());
				array.add(json);
			}
			pw = response.getWriter();
			pw.print(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}*/
}
