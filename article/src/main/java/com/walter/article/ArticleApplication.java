package com.walter.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArticleApplication {
    private static Logger logger = LoggerFactory.getLogger(ArticleApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
        logger.info("ArticleApplication启动成功");
    }
}
