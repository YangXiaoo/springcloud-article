package online.yangxiao.common.util;

import online.yangxiao.common.constant.SystemConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// 方法调用接口
public class RestResult<T> implements Serializable {

	private Integer resCode = 1;	// 返回状态码

	private Boolean resSuccess = true;	// 默认成功返回

	private String resMsg = "成功";		// 返回消息

	@SuppressWarnings("unchecked")
	private T resData = null;

	private Map<String, Object> resMap = new HashMap<String, Object>();


    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }


    public Map<String, Object> getResMap() {
        return resMap;
    }

    public void setResMap(Map<String, Object> resMap) {
        this.resMap = resMap;
    }

    public T getResData() {
        return resData;
    }

    public void setResData(T resData) {
        this.resData = resData;
    }

    public Boolean getResSuccess() {
        return resSuccess;
    }

    public void setResSuccess(Boolean resSuccess) {
        this.resSuccess = resSuccess;
    }

    public RestResult() {
    	this.resData = (T) new Object();
    	this.resMap = new HashMap<String, Object>();
    }

    public RestResult(Integer resCode, Boolean resSuccess, String resMsg) {
    	this.resCode = resCode;
    	this.resSuccess = resSuccess;
    	this.resMsg = resMsg;
    }

    public RestResult(Integer resCode, Boolean resSuccess, T resData) {
    	this.resCode = resCode;
    	this.resSuccess = resSuccess;
    	this.resData = resData;
    	this.resMap = new HashMap<String, Object>();
    }

    public RestResult(Integer resCode, Boolean resSuccess, 
    				  String resMsg, T resData) {
    	this.resCode = resCode;
    	this.resSuccess = resSuccess;
    	this.resMsg = resMsg;
    	this.resData = resData;
    	this.resMap = new HashMap<String, Object>();
    }
}