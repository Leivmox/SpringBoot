package com.itheima.springbootdb_review;

import domain.Comment;
import mapper.CommentMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootdbReviewApplicationTests {
    @Autowired
    private CommentMapper commentMapper;
    @Test
    public void selectComment(){
        Comment comment = commentMapper.findById(1);
        System.out.println(comment);
    }

}
