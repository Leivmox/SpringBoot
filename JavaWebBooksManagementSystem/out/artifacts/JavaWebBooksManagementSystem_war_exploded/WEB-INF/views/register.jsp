<%@ page import="com.demo.main.utils.Result" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>Register Page</title>
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
            cursor: pointer;
            display: flex;
            align-items: center;
        }

        .login-box-identity li > * {
            height: 100%;
        }

        .login-box-identity li p {
            margin-left: 4px;
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
<%--<%--%>
<%--    Result<String> result = (Result) request.getAttribute("data");--%>
<%--    System.out.println(result);--%>
<%--%>--%>
<%--<script lang="javascript">--%>
<%--    const e = <%=result == null ? false : result.getCode()%>--%>
<%--    if (e !== false && e !== 200) {--%>
<%--        alert("登录失败")--%>
<%--    }--%>
<%--</script>--%>
<div class="container-fluid login-container">
    <div class="login-box">
        <h3 class="login-box-title">图书管理系统</h3>
        <form action="/login" method="get" class="clearfix overflow-hidden">
            <input type="hidden" name="method" value="register">
            <div class="login-box-form">
                <div class="form-group row">
                    <label class="col-3">用户名</label>
                    <input type="text" class="form-control col-8" name="username">
                </div>
                <div class="form-group row">
                    <label class="col-3">密码</label>
                    <input type="password" class="form-control col-8" name="password">
                </div>
                <div class="form-group row">
                    <label class="col-3">手机号</label>
                    <input type="text" class="form-control col-8" name="phone">
                </div>
                <button type="submit" class="btn btn-primary" style="margin-top: 20px">注册</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
