package com.walter.article.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.text.DateFormat;
import java.util.Date;

/**
 * @ProjectName: Demo
 * @Package: com.example.demo.Utils
 * @ClassName: CommonUtils
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/11 18:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/11 18:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CommonUtils {
    /**
     * 获取格式为2018-12-11 16:19:45的时间
     *
     * @return
     */
    public static String getNow() {
        Date now = new Date();
        DateFormat format = DateFormat.getDateTimeInstance();
        return format.format(now);
    }

    /**
     * 将实体类转换为json格式
     *
     * @param o
     * @return
     */
    public static String convertToJson(Object o) {
        return JSONObject.toJSON(o).toString();
    }

    /**
     * 上传数据到指定url
     *
     * @param body
     */
    public static void sendToInterface(String url, String body) {
        HttpUtil.post(url, body);
    }

    /**
     * 清洗文章
     *
     * @param content
     * @return
     */
    public static String cleanArticle(String content) {
        Whitelist whitelist = Whitelist.basicWithImages()
                .addTags(new String[]{"table", "tr", "td", "tbody"})
                .removeTags("a")
                .addAttributes("td", new String[]{"colspan", "rowspan"})
                .addAttributes("img", "src", "data-src", "real_src", "real-src", "zoomfile", "oldsrc", "file", "data-original", "alt_src", "original-src");
        content = Jsoup.clean(content, whitelist);
        return content;
    }
}
