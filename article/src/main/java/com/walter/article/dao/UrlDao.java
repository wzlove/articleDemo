package com.walter.article.dao;

import com.walter.article.model.MongoDB.ArticleUrls;
import com.walter.article.model.Mysql.ArticleUrl;

import java.util.List;

/**
 * @ProjectName: Demo
 * @Package: com.example.demo.dao
 * @ClassName: UrlDao
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/12 14:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/12 14:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface UrlDao {
    //从数据库中读取数据只取没有上传的url
    List<ArticleUrls> getDatas();

    //保存数据到数据库中
    void save(ArticleUrls articleUrls);

    //查询所有数据,每次取n条
    List<ArticleUrls> getDatasByLimit(int n);

    //根据url和来源进行判重
    boolean getDataByUrl(String url, String source);

    //修改状态
    void changeFlag(String url);
    //失败链接,设置flag为999
    void changeFlagError(String url);
}
