package com.itheima.web.client;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Article;
import com.itheima.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description 测试用的Controller
 * @Date 2024-06-05
 */
@RestController
public class TestController {
    @Autowired
    private IArticleService articleServiceImpl;

    @GetMapping("/testArticleAllowComment")
    public String testArticleAllowComment() {
        // 获取第1页，每页1篇文章
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(1, 10);

        // 查找ID为12的文章
        Article targetArticle = null;
        for (Article article : pageInfo.getList()) {
            if (article.getId() == 12) {
                targetArticle = article;
                break;
            }
        }

        if (targetArticle == null) {
            return "Article with ID 12 not found.";
        }

        // 打印文章allowComment状态到控制台
        System.out.println("Article ID: " + targetArticle.getId());
        System.out.println("Title: " + targetArticle.getTitle());
        System.out.println("Allow Comment: " + targetArticle.getAllowComment());

        // 返回信息到客户端
        return "Article allowComment status has been printed to the console.";
    }
}
