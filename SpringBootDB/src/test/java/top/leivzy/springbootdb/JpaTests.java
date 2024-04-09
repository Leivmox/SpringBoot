package top.leivzy.springbootdb;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.leivzy.springbootdb.domain.Comment;
import top.leivzy.springbootdb.domain.Discuss;
import top.leivzy.springbootdb.mapper.CommentMapper;
import top.leivzy.springbootdb.repository.DiscussRepository;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)

@SpringBootTest
public class JpaTests {
    @Autowired
    private DiscussRepository repository;
    @Test
    public void selectComment() {
        Optional<Discuss> optional = repository.findById(1);
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }
        System.out.println();
    }

    @Test
    public void selectCommentByKeys() {
        List<Discuss> list = repository.findByAuthorNotNull();
        System.out.println(list);
    }

}
