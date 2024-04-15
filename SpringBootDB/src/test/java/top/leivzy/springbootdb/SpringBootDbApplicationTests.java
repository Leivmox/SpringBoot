package top.leivzy.springbootdb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.leivzy.springbootdb.domain.Article;
import top.leivzy.springbootdb.domain.Comment;
import top.leivzy.springbootdb.mapper.ArticleMapper;
import top.leivzy.springbootdb.mapper.CommentMapper;

@RunWith(SpringRunner.class)

@SpringBootTest
public class SpringBootDbApplicationTests {
@Autowired
    private CommentMapper commentMapper;
    private ArticleMapper articleMapper;

    @Test
    public void selectComment() {
        Comment comment = commentMapper.findById(1);
        System.out.println(comment);
    }

    @Test
    public void selectArticle() {
        Article article = articleMapper.selectArticle(1);
        System.out.println(article);
    }
}









