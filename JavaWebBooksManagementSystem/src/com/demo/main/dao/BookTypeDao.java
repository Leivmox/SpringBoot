package com.demo.main.dao;

import com.demo.main.entity.BookType;
import com.demo.main.utils.JdbcUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookTypeDao {

    public List<BookType> selectAll() {
        String sql = "select * from book_type";
        return resultSetToBookTypeList(JdbcUtil.query(sql));
    }

    private List<BookType> resultSetToBookTypeList(ResultSet resultSet) {
        List<BookType> bookTypes = new ArrayList<>();
        try {
            while (resultSet.next()) {
                BookType bookType = new BookType();
                bookType.setId(resultSet.getInt(1));
                bookType.setName(resultSet.getString(2));
                bookTypes.add(bookType);
            }
            return bookTypes;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BookType selectOne(int id) {
        String sql = "select * from book_type where id = ?";
        List<BookType> bookTypes = resultSetToBookTypeList(JdbcUtil.query(sql, id));
        return (bookTypes.size() > 0) ? bookTypes.get(0) : null;
    }

    public BookType selectByName(String name) {
        String sql = "select * from book_type where name = ?";
        List<BookType> bookTypes = resultSetToBookTypeList(JdbcUtil.query(sql, name));
        return (bookTypes.size() > 0) ? bookTypes.get(0) : null;
    }

    public void insertOne(BookType bookType) {
        String sql = "INSERT INTO `book_type` (name) VALUES (?)";
        JdbcUtil.insert(sql, true, bookType.getName());
    }

    public void deleteOne(int id) {
        String sql = "delete from `book_type` where id = ?";
        JdbcUtil.execute(sql, id);
    }

    public void updateOne(BookType bookType) {
        String sql = "update `book_type` set `name`= ? WHERE id = ?";
        JdbcUtil.execute(sql, bookType.getName(), bookType.getId());
    }

    public List<BookType> selectByNameLike(String name) {
        String sql = "select * from book_type where name like '%" + name + "%'";
        return resultSetToBookTypeList(JdbcUtil.query(sql));
    }
}
