package top.leivzy.springbootdb.mapper;

import top.leivzy.springbootdb.domain.Article;

public interface ArticleMapper {
    public Article selectArticle(Integer id);

    public int updateArticle(Article article);
}
