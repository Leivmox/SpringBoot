package com.itheima.web.client;

import com.github.pagehelper.PageInfo;
import com.itheima.model.ResponseData.ArticleResponseData;
import com.itheima.model.domain.Article;
import com.itheima.model.domain.Comment;
import com.itheima.service.IArticleService;
import com.itheima.service.ICommentService;
import com.itheima.utils.MyUtils;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Classname CommentController
 * @Description TODO
 * @Date 2019-3-14 10:23
 * @Created by CrazyStone
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private ICommentService commentServcieImpl;

    @Autowired
    private IArticleService articleServiceImpl;
    // 发表评论操作
    @PostMapping(value = "/publish")
    @ResponseBody
    public ArticleResponseData publishComment(HttpServletRequest request,@RequestParam Integer aid, @RequestParam String text) {
        // 去除js脚本
        text = MyUtils.cleanXSS(text);
        text = EmojiParser.parseToAliases(text);
        // 获取当前登录用户
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 封装评论信息
        Comment comments = new Comment();
        comments.setArticleId(aid);
        comments.setIp(request.getRemoteAddr());
        comments.setCreated(new Date());
        comments.setAuthor(user.getUsername());
        comments.setContent(text);
        try {
            commentServcieImpl.pushComment(comments);
            logger.info("发布评论成功，对应文章id: "+aid);
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("发布评论失败，对应文章id: "+aid +";错误描述: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    //查看评论
    @GetMapping(value = "/see/{id}")
    public String showComment(@PathVariable("id") Integer id,@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "count", defaultValue = "10") int count,HttpServletRequest request){
        PageInfo<Comment> pageInfo = commentServcieImpl.getComments(id,page,count);
        request.setAttribute("comments", pageInfo);
        return "/back/comment_list";
    }
    @PostMapping(value = "/delete")
    @ResponseBody
    public ArticleResponseData deleteComments(@RequestParam int id){
        int result = commentServcieImpl.deleteComments(id);

        return ArticleResponseData.ok();
    }
}

