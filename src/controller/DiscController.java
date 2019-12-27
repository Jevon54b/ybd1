package controller;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.regexp.internal.recompile;

import entity.Article;
import entity.Comment;
import entity.ServerResponse;
import service.DiscService;

@RequestMapping(value="/disc/")
@Controller
public class DiscController {

	@Autowired
	DiscService discService;
	
	@ResponseBody
	@RequestMapping(value="getArticleList.do",method=RequestMethod.GET)
	public ServerResponse getArticleList(HttpServletRequest rs) {
//responseData:articlePic�┠眞罍�、title、author、id
		
		List<Article> articlelist=discService.getArticleList();
		
		return ServerResponse.createBySuccess("SUCCESS", articlelist);
	}
	
	@ResponseBody
	@RequestMapping(value="getArticleDetail.do",method=RequestMethod.POST)
	public ServerResponse getArticleDetail(HttpServletRequest rs) {
//responseData:id、title、content、author、releasetime、pic
		String id = rs.getParameter("id");
		Article article=discService.getArticle(id);
//		System.out.println(article);
//		return ServerResponse.createBySuccess("SUCCESS", article);
		if(article!=null)
			return ServerResponse.createBySuccess("SUCCESS", article);
		return ServerResponse.createByErrorMessage("ERROR");
	}
	
	@ResponseBody
	@RequestMapping(value="getArticleComment.do",method=RequestMethod.POST)
	public ServerResponse getArticleComment(HttpServletRequest rs) {
//responseData:commentList
		String id = rs.getParameter("id");
		List<Comment> commentlist=discService.getArticleCommentList(id);
		if(commentlist!=null){
			return ServerResponse.createBySuccess("SUCCESS", commentlist);
		}
		return ServerResponse.createByErrorMessage("ERROR");
	}
	
	@ResponseBody
	@RequestMapping(value="addComment.do",method=RequestMethod.POST)
	public ServerResponse addComment(HttpServletRequest rs) {
//article_id、user_id、username、content
		String id = rs.getParameter("id");
		String article_id = rs.getParameter("article_id");
		String commenter = rs.getParameter("user_id");
		String content = rs.getParameter("content");
		String username = rs.getParameter("username");
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND); 
		String time = hour+":"+minute+":"+second;
		
		if(discService.addaComment(id, article_id, commenter, content, time))
		return ServerResponse.createBySuccess("SUCCESS");
		else {
			return ServerResponse.createByError();
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="deleteComment.do",method=RequestMethod.POST)
	public ServerResponse deleteComment(HttpServletRequest rs) {

		String id = rs.getParameter("id");
		boolean result=discService.deleteAComment(id);
		if (result)
			return ServerResponse.createBySuccess("SUCCESS");
		else
			return ServerResponse.createByError();
	}
	
	
	
	
	
}