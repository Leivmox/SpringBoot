package top.leivzy.springbootdb.mapper;

import org.apache.ibatis.annotations.*;
import top.leivzy.springbootdb.domain.Comment;


@Mapper
public interface CommentMapper {
    @Select("SELECT  * FROM t_comment WHERE id =#{id}")
    public Comment findById(Integer id);
    @Insert("INSERT INTO t_comment(content,author,a_id)" + "values (#{content},#{author},#{aId})")
    public int insertComment(Comment comment);
    @Update("UPDATE t_comment SET content=#{content} WHERE id=#{id}")
    public int updateComment(Comment comment);
    @Delete("DELETE FROM t_comment WHERE id=#{id}")
    public int deleteComment(Integer id);
}