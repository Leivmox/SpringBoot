<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.demo.main.utils.CommonUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.demo.main.entity.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>Books Page</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/index.css">
    <link rel="stylesheet" href="/resources/css/public.css">
    <style>
        .book-page {
            padding: 20px 20px 0;
        }
    </style>
</head>
<body>
<%
    // public
    String username = (String) request.getSession().getAttribute("username");
    String identity = CommonUtil.getIdentity();
    List<String[]> permissions = (List<String[]>) request.getAttribute("permissions");
    // private
    List<Book> books = (List<Book>) request.getAttribute("books");
%>
<div class="container-fluid">
    <div class="top-nav">
        <div class="top-nav-container">
            <div class="left-box">
                <p>图书管理系统</p>
            </div>
            <div class="right-box">
                <p class="top-nav-username" style="margin-right: 10px">用户：<%=username%></p>
                <p class="top-nav-identity">类型：<%=identity.equals("user") ? "用户" : "管理员"%></p>
            </div>
        </div>
    </div>
    <div class="non-top-nav">
        <div class="sidebar">
            <ul class="sidebar-ul">
<%--                <li><a href="/login?method=login-page">图书查询</a></li>--%>
<%--                <li><a href="#">图书查询</a></li>--%>
<%--                <li><a href="#">图书查询</a></li>--%>
                <c:forEach var="item" items="<%=permissions%>">
                    <li><a href="${item[1]}">${item[0]}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="main">
            <div class="book-page">
                <div class="query-form">
                    <form action="/book" method="post" class="clearfix"
                          style="width: 90%; margin: 0 auto 30px; /*position: fixed*/">
                        <div class="form-group row" style="margin: 0">
                            <input type="hidden" name="method" value="condition">
                            <input type="text" name="author" class="form-control col-3" placeholder="作者"/>
                            <input type="text" name="name" class="form-control col-3 offset-1" placeholder="书名"/>
                            <input type="submit" value="查询" class="btn btn-primary col-1 offset-4">
                        </div>
                    </form>
                </div>
                <div class="books-table">
                    <table class="table table-striped table-bordered table-hover" style="margin: 0 auto">
                        <thead>
                            <tr>
                                <td>图书ID</td>
                                <td>图书名称</td>
                                <td>图书作者</td>
                                <td>出版社</td>
                                <td>ISBN</td>
                                <td>图书描述</td>
                                <td>定价</td>
                                <td>是否被借阅</td>
                                <td>操作</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="<%=books%>">
                                <tr>
                                    <td>${book.id}</td>
                                    <td>${book.name}</td>
                                    <td>${book.author}</td>
                                    <td>${book.publisher}</td>
                                    <td>${book.isbn}</td>
                                    <td>${book.info}</td>
                                    <td>${book.pricing}</td>
                                    <td>${book.isBorrowed}</td>
                                    <td style="width: 149px">
                                        <a href="/book?method=borrow&id=${book.id}">
                                            <button class="btn btn-primary btn-sm">借阅</button>
                                        </a>
                                        <a href="/book?method=return&id=${book.id}">
                                            <button class="btn btn-success btn-sm">归还</button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
