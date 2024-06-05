package com.itheima.model.domain;


public class Category {
    private String categories; // 文章分类
    private Integer categoriesNum;  // 评论总量

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Integer getCategoriesNum() {
        return categoriesNum;
    }

    public void setCategoriesNum(Integer categoriesNum) {
        this.categoriesNum = categoriesNum;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categories='" + categories + '\'' +
                ", categoriesNum=" + categoriesNum +
                '}';
    }
}
