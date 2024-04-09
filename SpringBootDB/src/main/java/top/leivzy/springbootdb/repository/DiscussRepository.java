package top.leivzy.springbootdb.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.leivzy.springbootdb.domain.Discuss;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

public interface DiscussRepository extends JpaRepository<Discuss, Integer> {
    public List<Discuss> findByAuthorNotNull();

    @Query("SELECT c FROM t_comment c WHERE c.aId = ?1")
    public List<Discuss> getDiscussPaged(Integer aid, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE t_comment c SET c.author= ?1 WHERE c.id = ?2")
    public int updateAuthor(String author, Integer id);

    @Transactional
    @Modifying
    @Query("DELETE t_comment c WHERE c.id= ?1")
    public int deleteDiscussWithoutAuthor(Integer id);
}
