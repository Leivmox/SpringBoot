package com.rabbiter.bms.web;

import com.rabbiter.bms.service.BookInfoService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import com.rabbiter.bms.model.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // 表示该类是一个 RESTful 控制器，返回的数据会自动转换为 JSON 格式
@RequestMapping(value = "/bookInfo") // 定义 URL 的公共前缀 /bookInfo
public class BookInfoController {

    @Autowired // 自动注入 BookInfoService 对象，用于处理图书相关的业务逻辑
    BookInfoService bookInfoService;

    // 获取图书的总数量
    @GetMapping(value = "/getCount") // GET 请求，URL 为 /bookInfo/getCount
    public Integer getCount() {
        // 调用服务层的方法获取图书数量
        return bookInfoService.getCount();
    }

    // 查询所有图书信息
    @GetMapping(value = "/queryBookInfos") // GET 请求，URL 为 /bookInfo/queryBookInfos
    public List<BookInfo> queryBookInfos() {
        // 调用服务层方法查询所有图书并返回结果列表
        return bookInfoService.queryBookInfos();
    }

    // 分页和条件查询图书信息
    // 接受 page（页码）、limit（每页条数）、bookname、bookauthor、booktypeid 等查询参数
    @GetMapping(value = "/queryBookInfosByPage") // GET 请求，URL 为 /bookInfo/queryBookInfosByPage
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params) {
        // 调用工具方法，将 page 和 limit 参数转换为 begin 和 size，用于分页查询
        MyUtils.parsePageParams(params);

        // 调用服务层方法，获取符合条件的图书总数量
        int count = bookInfoService.getSearchCount(params);

        // 调用服务层方法，进行分页查询图书信息
        List<BookInfo> bookInfos = bookInfoService.searchBookInfosByPage(params);

        // 将状态码、消息、总数量和分页查询的图书信息封装在结果 Map 中返回
        return MyResult.getListResultMap(0, "success", count, bookInfos);
    }

    // 添加一本新的图书信息
    @PostMapping(value = "/addBookInfo") // POST 请求，URL 为 /bookInfo/addBookInfo
    public Integer addBookInfo(@RequestBody BookInfo bookInfo) {
        // 接收图书信息对象（JSON 格式）并调用服务层方法添加到数据库
        return bookInfoService.addBookInfo(bookInfo);
    }

    // 删除指定的图书信息
    @DeleteMapping(value = "/deleteBookInfo") // DELETE 请求，URL 为 /bookInfo/deleteBookInfo
    public Integer deleteBookInfo(@RequestBody BookInfo bookInfo) {
        // 接收要删除的图书信息对象（JSON 格式）并调用服务层方法进行删除
        return bookInfoService.deleteBookInfo(bookInfo);
    }

    // 删除多本图书信息
    @DeleteMapping(value = "/deleteBookInfos") // DELETE 请求，URL 为 /bookInfo/deleteBookInfos
    public Integer deleteBookInfos(@RequestBody List<BookInfo> bookInfos) {
        // 接收一个包含多个图书对象的列表，并调用服务层方法删除这些图书
        return bookInfoService.deleteBookInfos(bookInfos);
    }

    // 更新图书信息
    @PutMapping(value = "/updateBookInfo") // PUT 请求，URL 为 /bookInfo/updateBookInfo
    public Integer updateBookInfo(@RequestBody BookInfo bookInfo) {
        // 接收图书信息对象（JSON 格式）并调用服务层方法更新图书信息
        return bookInfoService.updateBookInfo(bookInfo);
    }
}
