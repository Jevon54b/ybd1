package dao;

import java.util.List;
import java.util.Map;

import entity.Article;
import entity.Comment;

public interface DiscDaoMapper {
	//DaoMapper����JDBC�������ݿ�
	
	//ͨ��id��������
    Article selectArticleById(String id);
    
    //��ȡȫ������
    List<Article> getAllArticles(); 
    
    //ͨ������id��������
    Comment selectCommentById(String id);

    //��������id��������
    List<Comment> getAllCommentByID(String id);
    
    //�������
    int addArticle(Map<String,Object> map);
    
    //�������
    int addComment(Map<String,Object> map);
    
    //ɾ����,ע�������۵�id
    int deleteComment(String id);
    
    //ɾ���£����µ�id
    int deleteArticle(String id);
    
    int AddArticleReadNum(String article_id);
    
    int AddArticleCommentNum(String article_id);
    
    int SubArticleCommentNum(String article_id);
 
}
