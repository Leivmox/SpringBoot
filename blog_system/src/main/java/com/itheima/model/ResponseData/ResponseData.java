package com.itheima.model.ResponseData;

public class ResponseData {
    private boolean success;
    private String msg;

    public static ResponseData ok() {
        ResponseData response = new ResponseData();
        response.setSuccess(true);
        response.setMsg("操作成功");
        return response;
    }

    public static ResponseData fail() {
        ResponseData response = new ResponseData();
        response.setSuccess(false);
        response.setMsg("操作失败");
        return response;
    }

    public static ResponseData fail(String msg) {
        ResponseData response = new ResponseData();
        response.setSuccess(false);
        response.setMsg(msg);
        return response;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
