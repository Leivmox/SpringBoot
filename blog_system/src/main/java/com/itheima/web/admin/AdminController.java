package com.itheima.web.admin;

import com.github.pagehelper.PageInfo;
import com.itheima.model.ResponseData.ArticleResponseData;
import com.itheima.model.ResponseData.StaticticsBo;
import com.itheima.model.domain.Article;
import com.itheima.model.domain.Comment;
import com.itheima.model.domain.User;
import com.itheima.service.IArticleService;
import com.itheima.service.ISiteService;
import com.itheima.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname AdminController
 * @Description 后台管理模块
 * @Date 2019-3-14 10:39
 * @Created by CrazyStone
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ISiteService siteServiceImpl;
    @Autowired
    private IArticleService articleServiceImpl;

    @Autowired
    private IUserService userServiceImpl;


    // 管理中心起始页
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        // 获取最新的5篇博客、评论以及统计数据
        List<Article> articles = siteServiceImpl.recentArticles(5);
        List<Comment> comments = siteServiceImpl.recentComments(5);
        StaticticsBo staticticsBo = siteServiceImpl.getStatistics();
        // 向Request域中存储数据
        request.setAttribute("comments", comments);
        request.setAttribute("articles", articles);
        request.setAttribute("statistics", staticticsBo);
        return "back/index";
    }

    // 向文章发表页面跳转
    @GetMapping(value = "/article/toEditPage")
    public String newArticle() {
        return "back/article_edit";
    }

    // 发表文章
    @PostMapping(value = "/article/publish")
    @ResponseBody
    public ArticleResponseData publishArticle(Article article) {
        if (StringUtils.isBlank(article.getCategories())) {
            article.setCategories("默认分类");
        }
        try {
            articleServiceImpl.publish(article);
            logger.info("文章发布成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章发布失败，错误信息: " + e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 跳转到后台文章列表页面
    @GetMapping(value = "/article")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "count", defaultValue = "10") int count,
                        HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    // 向文章修改页面跳转
    @GetMapping(value = "/article/{id}")
    public String editArticle(@PathVariable("id") String id, HttpServletRequest request) {
        Article article = articleServiceImpl.selectArticleWithId(Integer.parseInt(id));
        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());
        return "back/article_edit";
    }

    // 文章修改处理
    @PostMapping(value = "/article/modify")
    @ResponseBody
    public ArticleResponseData modifyArticle(Article article) {
        try {
            articleServiceImpl.updateArticleWithId(article);
            logger.info("文章更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章更新失败，错误信息: " + e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 文章删除
    @PostMapping(value = "/article/delete")
    @ResponseBody
    public ArticleResponseData delete(@RequestParam int id) {
        try {
            articleServiceImpl.deleteArticleWithId(id);
            logger.info("文章删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章删除失败，错误信息: " + e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 向文章预览页面跳转
    @GetMapping(value = "/article/preview/{id}")
    @ResponseBody
    public Article previewArticle(@PathVariable("id") int id) {
        Article article = articleServiceImpl.selectArticleWithId(id);
        return article;
    }

    //打开评论
    @PutMapping("/opencom")
    @ResponseBody
    public ArticleResponseData opencom(@RequestParam int id) {
        try {
            articleServiceImpl.openAllowCommentByID(id);
            logger.info("开启成功");
            return ArticleResponseData.ok();

        } catch (Exception e) {
            logger.error("开启失败，错误信息: " + e.getMessage());
            return ArticleResponseData.fail();

        }
    }

    //关闭评论
    @PutMapping("/closecom")
    @ResponseBody
    public ArticleResponseData closecom(@RequestParam int id) {
        try {
            articleServiceImpl.closeAllowCommentByID(id);
            logger.info("关闭成功");
            return ArticleResponseData.ok();

        } catch (Exception e) {
            logger.error("关闭失败，错误信息: " + e.getMessage());
            return ArticleResponseData.ok();
        }
    }

    // 评论编辑
    @GetMapping(value = "/comment")
    public String comment(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "count", defaultValue = "10") int count,
                          HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.removeAttribute("articles");
        request.setAttribute("articles", pageInfo);
//        request.setAttribute("articles.allowComment", 0);

        return "/back/comment_edit";
    }

    // 用户管理
    @GetMapping(value = "/setting")
    public String setting(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "count", defaultValue = "10") int count,
                          HttpServletRequest request) {
        PageInfo<User> pageInfo = userServiceImpl.getAllUsers(page, count);
        request.setAttribute("users", pageInfo);
        return "back/user_list";
    }

    // 用户删除
    @PostMapping("/user/delete")
    @ResponseBody
    public ArticleResponseData deleteUser(@RequestParam int id) {
        try {
            userServiceImpl.deleteUserById(id);
            logger.info("用户删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("用户删除失败，错误信息: " + e.getMessage());
            return ArticleResponseData.fail();
        }
    }
}


