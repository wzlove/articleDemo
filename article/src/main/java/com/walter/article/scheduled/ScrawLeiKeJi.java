package com.walter.article.scheduled;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.walter.article.dao.UrlDao;
import com.walter.article.model.Constant;
import com.walter.article.model.MongoDB.ArticleUrls;
import com.walter.article.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开启定时任务抓取雷科技的详情url
 * @ProjectName: Demo
 * @Package: com.example.demo.articleScraw
 * @ClassName: ScrawLeiKeJi
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/12 11:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/12 11:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Component
@RestController
public class ScrawLeiKeJi {

    @Autowired
    @Qualifier("urlDao")
    private UrlDao urlDao;

    private Logger logger = LoggerFactory.getLogger(ScrawLeiKeJi.class);
//    http://www.leikeji.com/columns/getArticleList?ifHome=1&status=1&channels=1&pageIndex=1&pageSize=20&orderBy=postDate&orderType=desc&colName=看点
    private static String CommonUrl="http://www.leikeji.com/columns/getArticleList?ifHome=1&status=1&channels=1&pageIndex=";
    private static String CategoryKANDIANUrl="&pageSize=20&orderBy=postDate&orderType=desc&colName=%E7%9C%8B%E7%82%B9";
    private static String Source="雷科技";

    @RequestMapping("/test")
    @Scheduled(cron = "0 34 19 * * ? ")
    public void begin(){
        for(int i=1;i<=3;i++){
            scrawUrl(i);
        }
    }
//    @Scheduled(cron = "0 01 19 * * ? ")
    public void scrawUrl(int page){
//        int page=1;

        logger.info("开始爬取第"+page+"页,当前时间为："+ CommonUtils.getNow());
        String url=CommonUrl+page+CategoryKANDIANUrl;
        String data= HttpUtil.get(url);
//        System.out.println(data);
        JSONArray jsonArray= JSON.parseObject(data).getJSONArray("data");
        for(int i=0;i<jsonArray.size();i++){
//            logger.info("第"+page+"页，第"+i+"条");
            ArticleUrls articleUrls=new ArticleUrls();
            String itemData=jsonArray.get(i).toString();
            String detaiUrl=JSON.parseObject(itemData).getString("appweburl");
            detaiUrl=detaiUrl.replace("appweb/articles","article");//详情url

            String date= CommonUtils.getNow();
            String flag= Constant.UnFinished;
            articleUrls.setFlag(flag);
            articleUrls.setDate(date);
            articleUrls.setPage(page);
            articleUrls.setUrl(detaiUrl);
            articleUrls.setSource(Source);
            if(!urlDao.getDataByUrl(detaiUrl,Source)){
                urlDao.save(articleUrls);
            }else {
                logger.info("这条数据重复了，"+detaiUrl);
            }

        }
        logger.info("当前第"+page+"页已完成,完成时间为："+ CommonUtils.getNow());

    }


}
