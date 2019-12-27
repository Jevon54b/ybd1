package impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DiscDaoMapper;
import dao.DistributerDaoMapper;
import entity.Article;
import entity.Comment;
import entity.Distributer;
import service.DiscService;
import service.DistributerService;

@Service
public class DiscServiceImpl implements DiscService {


	@Autowired
	DiscDaoMapper distMapper;
	
	@Override
	public Article getArticle(String id){
		return distMapper.selectArticleById(id);
	}
	
	@Override
	public List<Comment> getArticleCommentList(String id){
		return distMapper.getAllCommentByID(id);
	}
		
	@Override
	public List<Article> getArticleList(){
		return distMapper.getAllArticles();
	}

	@Override
	public boolean addaComment(String id, String article_id, String commenter,
			String content, String time) {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("article_id", article_id);
		map.put("commenter", commenter);
		map.put("time", time);
		map.put("content", content);
		
		int result=distMapper.addComment(map);
		if (result==1)
			return true;
		else 
			return true;
	}

	@Override
	public boolean deleteAComment(String id) {
		distMapper.deleteComment(id);
		return true;
	}

	@Override
	public boolean deleteAnArticle(String id) {
		distMapper.deleteArticle(id);
		return true;
	}
	
	
	
}
