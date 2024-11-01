package com.demo.main.controller;

import com.demo.main.entity.Book;
import com.demo.main.entity.BookType;
import com.demo.main.entity.User;
import com.demo.main.service.BookService;
import com.demo.main.service.BookTypeService;
import com.demo.main.service.BorrowingService;
import com.demo.main.service.UserService;
import com.demo.main.utils.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/router")
public class RouterServlet extends HttpServlet {

    BookService bookService = new BookService();
    BorrowingService borrowingService = new BorrowingService();
    UserService userService = new UserService();
    BookTypeService bookTypeService = new BookTypeService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = request.getParameter("page");

        // 设置权限
        request.setAttribute("permissions", CommonUtil.permissions.get(CommonUtil.getIdentity()));

        switch (page) {
            // /router?page=book
            case "book":
                if (request.getAttribute("books") == null) {
                    request.setAttribute("books", bookService.selectAll());
                }
                request.getRequestDispatcher("/WEB-INF/views/book.jsp").forward(request, response);
                break;
            case "borrowing":
                request.setAttribute("borrowings", CommonUtil.getIdentity().equals("admin") ? borrowingService.selectVoAll() : borrowingService.selectVoByCurrentUser());
                request.getRequestDispatcher("/WEB-INF/views/borrowing.jsp").forward(request, response);
                break;
            case "user_profile":
                request.getRequestDispatcher("/WEB-INF/views/user_profile.jsp").forward(request, response);
                break;
            case "admin_user_management":
                if (request.getAttribute("users") == null) {
                    request.setAttribute("users", userService.selectAll());
                }
                request.getRequestDispatcher("/WEB-INF/views/admin_user_management.jsp").forward(request, response);
                break;
            case "admin_add_user":
                request.getRequestDispatcher("/WEB-INF/views/admin_add_user.jsp").forward(request, response);
                break;
            case "admin_user_update":
                int id = Integer.parseInt(request.getParameter("id"));
                User user = userService.selectOne(id);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/admin_user_update.jsp").forward(request, response);
                break;
            case "admin_book_type_management":
                if (request.getAttribute("types") == null) {
                    request.setAttribute("types", bookTypeService.selectAll());
                }
                request.getRequestDispatcher("/WEB-INF/views/admin_book_type_management.jsp").forward(request, response);
                break;
            case "admin_book_type_update":
                id = Integer.parseInt(request.getParameter("id"));
                BookType bookType = bookTypeService.selectOne(id);
                request.setAttribute("type", bookType);
                request.getRequestDispatcher("/WEB-INF/views/admin_book_type_update.jsp").forward(request, response);
            case "admin_book_type_insert":
                request.getRequestDispatcher("/WEB-INF/views/admin_add_book_type.jsp").forward(request, response);
                break;
            case "admin_book_management":
                if (request.getAttribute("books") == null) {
                    request.setAttribute("books", bookService.selectAll());
                }
                request.getRequestDispatcher("/WEB-INF/views/admin_book_management.jsp").forward(request, response);
                break;
            case "admin_add_book":
                request.getRequestDispatcher("/WEB-INF/views/admin_add_book.jsp").forward(request, response);
                break;
            case "admin_book_update":
                id = Integer.parseInt(request.getParameter("id"));
                Book book = bookService.selectOne(id);
                request.setAttribute("book", book);
                request.getRequestDispatcher("/WEB-INF/views/admin_book_update.jsp").forward(request, response);
                break;
        }
    }
}
