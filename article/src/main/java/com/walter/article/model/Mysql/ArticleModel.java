package com.walter.article.model.Mysql;

//import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @ProjectName: Demo
 * @Package: com.example.demo.Model
 * @ClassName: ArticleModel
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/4 14:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/4 14:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@Document(collection = "Article")
public class ArticleModel implements Serializable {

    @Value("wangzheng")
    private String creator;//创建人***
//    @NotNull
    private String site;//站点***
//    @NotNull
    private String title;//标题***
//    @NotNull
    private String sourceLink;//链接地址***
    private String author;//作者/账户名
//    @NotNull
    private String from;//来源*****
//    @NotNull
    private String content;//内容****
//    @NotNull
    private String publishTime;//文章发布时间****
//    @NotNull
    private String crawlTime;//抓取时间****
    private String coverUrl;//封面图
    private String summary;//摘要
    private String keyword;//关键词
//    @NotNull
    private String category;//分类****
    private int commentCount;//评论数
    private int likeCount;//点赞数
    private int fanCount;//粉丝数
    private int clickCount;//点击数
    private int forwardCount;//转发数
    private int dislikeCount;//不喜欢数
    private int collectCount;//收藏数
    private String commentJson;//热门评论JSON
//    @NotNull
    private String type;//类型  类型(文章article，图文幻灯image)****


}
