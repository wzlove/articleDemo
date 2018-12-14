package com.walter.article.dao.impl;

import com.walter.article.dao.UrlDao;
import com.walter.article.model.Constant;
import com.walter.article.model.MongoDB.ArticleUrls;
import com.walter.article.model.Mysql.ArticleUrl;
import org.apache.commons.net.nntp.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: Demo
 * @Package: com.example.demo.dao.impl
 * @ClassName: UrlDaoImpl
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/12 14:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/12 14:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Component
@Repository("urlDao")
public class UrlDaoImpl implements UrlDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ArticleUrls> getDatas() {
        Query query = new Query(Criteria.where("flag").is(Constant.UnFinished));
        return  mongoTemplate.findAll(ArticleUrls.class);
    }

    @Override
    public List<ArticleUrls> getDatasByLimit(int n) {
        Query query=new Query(Criteria.where("flag").is(Constant.UnFinished));
        query.limit(n);
        return mongoTemplate.find(query,ArticleUrls.class);
    }

    @Override
    public void save(ArticleUrls articleUrls) {
        mongoTemplate.save(articleUrls);
    }

    @Override
    public boolean getDataByUrl(String url,String source) {
        Query query = new Query(Criteria.where("source").is(source));

        query.addCriteria(Criteria.where("url").is(url));
        int size = mongoTemplate.find(query, ArticleUrls.class).size();
        if(size>0){//说明已存在
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void changeFlag(String url) {
        Update update=new Update();
        update.set("flag",Constant.Finished);
        Query query=new Query(Criteria.where("url").is(url));
        mongoTemplate.updateFirst(query,update,ArticleUrls.class);
    }

    @Override
    public void changeFlagError(String url) {
        Update update=new Update();
        update.set("flag",Constant.Error);
        Query query=new Query(Criteria.where("url").is(url));
        mongoTemplate.updateFirst(query,update,ArticleUrls.class);
    }
}
