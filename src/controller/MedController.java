package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.ServerResponse;
import service.MedService;

@Controller
@RequestMapping(value="/medicine/")
public class MedController {
	
	@Autowired
	MedService ms;
	
	@ResponseBody
	@RequestMapping(value="getTop5MedList.do",method=RequestMethod.GET)
	public ServerResponse getTop3MedList(HttpServletRequest rs) {
		return ServerResponse.createBySuccess("成功获取药品", ms.getTopSalesnumMedList());
	}
	
	@ResponseBody
	@RequestMapping(value="getMedicineListByTypeId.do",method=RequestMethod.GET)
	public ServerResponse getMedicineListByType(HttpServletRequest rs) {
		int t_id = Integer.parseInt(rs.getParameter("type_id"));
		int sort_way = Integer.parseInt(rs.getParameter("sort_flag"));
		return ServerResponse.createBySuccess("成功获取药品", ms.getMedListByType(t_id,sort_way));
	}
	
	@ResponseBody
	@RequestMapping(value="getMedicineDetail.do",method=RequestMethod.GET)
	public ServerResponse getMedicineDetail(HttpServletRequest rs) {
		int m_id = Integer.parseInt(rs.getParameter("med_id"));
		return ServerResponse.createBySuccess("成功获取药品详请",ms.getMedDetailById(m_id) );
	}
	
	@ResponseBody
	@RequestMapping(value="searchMed.do",method=RequestMethod.GET)
	public ServerResponse searchMed(HttpServletRequest rs) {
		String keyword = rs.getParameter("keyword");
		int sort_way = Integer.parseInt(rs.getParameter("sort_flag"));
		return ServerResponse.createBySuccess("成功获取药品", ms.searchMedByKeyword(keyword,sort_way));
	}

}
