<%@ page import="com.demo.main.utils.Result" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/index.css">
    <style>
        .login-container {
            height: 100%;
            background-size: auto 100% !important;
            background: #CCCCCC url(/resources/img/bg1.jpg) no-repeat fixed center right;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-box {
            margin-top: -100px;
            background-color: rgba(255, 255, 255, .8);
            width: 400px;
            height: 450px;
            border-radius: var(--radius);
            box-sizing: border-box;
            padding: 30px;
            position: relative;
            border: 2px solid #0F63BBFF;
        }

        .login-box-identity {
            display: flex;
            padding: 0;
            margin-top: 40px;
            height: 30px;
        }

        .login-box-identity li {
            height: 28px;
            display: flex;
            align-items: center;
            line-height: 26px;
        }

        .login-box-identity li > * {
            display: block;
            height: 100%;
            align-items: center;
            margin: 0;
        }

        .login-box-identity li > label {
            margin-left: 8px;
        }

        .login-box-identity li:hover {
            color: var(--primary);
        }

        .login-box-title {
            text-align: center;
        }

        .login-box-form {
            margin-top: 40px;
        }

        .login-box-register {
            position: absolute;
            bottom: 30px;
        }

        .login-box-register a {
            color: var(--purple);
        }
    </style>
</head>
<body>
<%
    Result<String> result = (Result) request.getAttribute("data");
%>
<script lang="javascript">
    const e =
    <%=result == null ? false : result.getCode()%>
    if (e) {
        alert("<%=(result == null) ? "" : result.getData()%>")
    }
</script>
<div class="container-fluid login-container">
    <div class="login-box">
        <h3 class="login-box-title">图书管理系统</h3>
        <form action="/login" method="post" class="clearfix">
            <ul class="login-box-identity">
                <li style="margin-right: 18px">
                    <input type="radio" name="identity" checked value="user"
                           id="identity-user">
                    <label for="identity-user">用户</label>
                </li>
                <li>
                    <input type="radio" name="identity" value="admin"
                           id="identity-admin">
                    <label for="identity-admin">管理员</label>
                </li>
            </ul>
            <div class="login-box-form">
                <div class="form-group row">
                    <label class="col-3">用户名</label>
                    <input type="text" class="form-control col-8" name="username">
                </div>
                <div class="form-group row">
                    <label class="col-3">密码</label>
                    <input type="password" class="form-control col-8" name="password">
                </div>
                <button type="submit" class="btn btn-primary" style="margin-top: 20px">登录</button>
            </div>
        </form>
        <div class="login-box-register">
            <a href="/login?method=register-page">用户注册</a>
        </div>
    </div>
</div>
</body>
</html>
