package top.leivzy.springbootdb;
import org.hibernate.criterion.Example;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.leivzy.springbootdb.domain.Article;
import top.leivzy.springbootdb.domain.Comment;
import top.leivzy.springbootdb.domain.Discuss;
import top.leivzy.springbootdb.mapper.ArticleMapper;
import top.leivzy.springbootdb.mapper.CommentMapper;
import top.leivzy.springbootdb.repository.DiscussRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)

@SpringBootTest
public class SpringBootDbApplicationTests {
@Autowired
    private CommentMapper commentMapper;
    private ArticleMapper articleMapper;
    private DiscussRepository repository;

    @Test
    public void selectComment() {
        Optional<Discuss> optional = repository.findById(1);
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }
    }

    @Test
    public void selectCommentByKey() {
        List<Discuss> list = repository.findByAuthorNotNull();
        System.out.println(list);
    }

    @Test
    public void selectCommentPaged() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Discuss> allPaged = repository.getDiscussPaged(1, pageable);
        System.out.println(allPaged);
    }
    @Test
    public void selectConmmentByExample() {
        Discuss discuss = new Discuss();
        discuss.setAuthor("张三");
        Example<Discuss> example = Example.of(discuss);
        List<Discuss> list = repository.findAll(example);
        System.out.println(list);
    }

    @Test
    public void selectCommentByExampleMatcvher() {
        Discuss discuss = new Discuss();
        discuss.setAuthor("张");
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("author", startsWith());
        Example<Discuss> example = Example.of(discuss, matcher);
        List<Discuss> list = repository.findAll(example);
        System.out.println(list);
    }
/*    @Test
    public void selectComment() {
        Comment comment = commentMapper.findById(1);
        System.out.println(comment);
    }*/

    @Test
    public void selectArticle() {
        Article article = articleMapper.selectArticle(1);
        System.out.println(article);
    }
}









