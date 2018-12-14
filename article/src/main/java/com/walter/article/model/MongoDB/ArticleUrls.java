package com.walter.article.model.MongoDB;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ProjectName: Demo
 * @Package: com.example.demo.Model.MongoDB
 * @ClassName: ArticleUrls
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/12 14:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/12 14:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@Document(collection = "ArticleUrls")
public class ArticleUrls {
    @Id
    private String id;
    private String url;//url
    private String flag;//0表示未解析上传，1表示解析并上传
    private String date;//入库时间
    private int page;//当前页
    private String source;//来源
}
