package com.walter.article.scheduled;

import com.walter.article.dao.UrlDao;
import com.walter.article.model.MongoDB.ArticleUrls;
import com.walter.article.model.Mysql.ArticleModel;
import com.walter.article.utils.CommonUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @ProjectName: article
 * @Package: com.walter.article.scheduled
 * @ClassName: UploadLeikeji
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/14 10:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/14 10:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Component
@RestController
public class UploadLeikeji {
    @Autowired
    @Qualifier("urlDao")
    private UrlDao urlDao;

    private Logger logger = LoggerFactory.getLogger(UploadLeikeji.class);
    public static String URL_40460 = "http://pmop.staff.ifeng.com/Cmpp/runtime/interface_40460.jhtml";

    @RequestMapping("/upload")
    @Scheduled(cron = "0 0/2 * * * ? ")
    public void upload() throws IOException {
        List<ArticleUrls> list = urlDao.getDatasByLimit(20);
        for (ArticleUrls articleUrls : list) {
            ArticleModel articleModel = parse(articleUrls.getUrl());
            if (articleModel==null) {
                urlDao.changeFlagError(articleUrls.getUrl());
                continue;
            } else {
                urlDao.changeFlag(articleUrls.getUrl());
                String body = CommonUtils.convertToJson(articleModel);
                CommonUtils.sendToInterface(URL_40460, body);
                logger.info(articleModel.getSourceLink() + "上传成功！");
            }

        }
    }


    public ArticleModel parse(String url) {

        ArticleModel articleModel = new ArticleModel();
//        String url ="http://www.leikeji.com/article/22715";
        String creator = "wangzheng";//创建人
        String site = "雷科技";//站点
        String from = "雷科技";//来源
        String category = "科技";//分类
        String sourceLink = url;//链接地址
        String crawlTime = CommonUtils.getNow();//抓取时间
        String type = "article";//类型
        Connection connection = Jsoup.connect(url);
        connection.header("host", "www.leikeji.com")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:63.0) Gecko/20100101 Firefox/63.0")
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("accept-language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                .header("accept-encoding", "gzip, deflate")
                .header("referer", "http://www.leikeji.com/columns/articles/%E7%9C%8B%E7%82%B9")
                .header("connection", "keep-alive")
                .header("cookie", "leikeji=s%3A7kaJWjNyAIOQUg_kEJowFizJfeQ8TguL.gLAzSwjgsqS9xmyRaoY00%2BFtw005gbkbtDEz24m%2F2oo; UM_distinctid=167a046f241df-0f549114bd11db8-1160684a-1fa400-167a046f242d6; CNZZDATA1000394757=413018089-1544581041-http%253A%252F%252Ftech.163.com%252F%7C1544772602")
                .header("upgrade-insecure-requests", "1")
                .header("cache-control", "no-cache")
                .header("postman-token", "18e9a10d-0a2b-0010-ac93-4c25cb713399");
        Document document = null;
        try {
            document = connection.get();
            Elements select = document.getElementsByClass("l-s f-sec");//文章部分
            String title = select.select(".article-title").text();//文章标题
            String author = select.select(".username").text();//作者
            String content = select.select(".article-content").toString();//内容
            content = CommonUtils.cleanArticle(content);
            String publishTime = select.select(".time-info").text();//发布时间
            if (publishTime.contains("|"))
                publishTime = publishTime.replace("|", "");
            String likeCount = select.select(".iconfont.icon-comment").text();//点赞数
            String commentCount = select.select(".iconfont.icon-comment").text();//评论数

            articleModel.setAuthor(author);
            articleModel.setCreator(creator);
            articleModel.setSite(site);
            articleModel.setFrom(from);
            articleModel.setSourceLink(sourceLink);
            articleModel.setCrawlTime(crawlTime);
            articleModel.setType(type);
            articleModel.setTitle(title);
            articleModel.setContent(content);
            articleModel.setPublishTime(publishTime);
            articleModel.setCategory(category);
            articleModel.setCommentCount(Integer.parseInt(commentCount));
            articleModel.setLikeCount(Integer.parseInt(likeCount));

            return articleModel;
        } catch (IOException e) {
            logger.info("异常哦，此条url失效了" + url);
        }


        return null;
    }
}
