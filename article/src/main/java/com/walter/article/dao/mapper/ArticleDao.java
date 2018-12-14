package com.walter.article.dao.mapper;

import com.walter.article.model.Mysql.ArticleUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ProjectName: Demo
 * @Package: com.example.demo.dao
 * @ClassName: ArticleDao
 * @Description: java类作用描述
 * @Author: 唐朝
 * @CreateDate: 2018/12/10 18:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/10 18:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Mapper
public interface ArticleDao {
    //查询所有数据
    List<ArticleUrl> getDatas();
    //查询所有数据,每次取n条
    List<ArticleUrl> getDatasByLimit(int n);
    //保存所有数据
    void saveData(ArticleUrl articleUrl);
    //根据href查询数据
    boolean getDataByHref(String href);
    //修改状态
    void changeFlag(String href);
}
