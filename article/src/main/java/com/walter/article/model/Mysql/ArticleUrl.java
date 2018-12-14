package com.walter.article.model.Mysql;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: Demo
 * @Package: com.example.demo.Model
 * @ClassName: ArticleUrl
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/10 17:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/10 17:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
public class ArticleUrl implements Serializable {
    private  int id;
    private String url;
    private String flag;
    private Date date;
    private String page;
}
