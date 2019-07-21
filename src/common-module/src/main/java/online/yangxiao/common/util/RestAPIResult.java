package online.yangxiao.common.util;

import online.yangxiao.common.constant.SystemConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RestAPIResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int resCode = 1;

    private String resMsg = "成功！";

    @SuppressWarnings("unchecked")
    private T resData = (T) new Object();

    private Map<String, Object> resMap = new HashMap<String, Object>();

    private String signature;

    private String deviceType;

    private String dataCode;

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

    public T getResData() {
        return resData;
    }

    public void setResData(T resData) {
        this.resData = resData;
    }

    public Map<String, Object> getResMap() {
        return resMap;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setResMap(Map<String, Object> resMap) {
        this.resMap = resMap;
    }
    public RestAPIResult(String errorMsg){
        this.resMsg = errorMsg;
        this.resCode = SystemConstants.Code.error;
        this.resData = (T) new Object();
        this.resMap = new HashMap<String, Object>();
    }

    public RestAPIResult(){
        this.resData = (T) new Object();
        this.resMap = new HashMap<String, Object>();
    }

    public void success(T object){
        this.resCode = SystemConstants.Code.success;
        this.resMsg = SystemConstants.Code.SUCCESS;
        this.resData = object;
        this.resMap = new HashMap<String, Object>();
    }
    public void error(){
        this.resCode = SystemConstants.Code.error;
        this.resMsg = SystemConstants.Code.FAIL;
        this.resData = (T) new Object();
        this.resMap = new HashMap<String, Object>();
    }
    public void error(String message){
        this.resCode = SystemConstants.Code.error;
        this.resMsg = message;
        this.resData = (T) new Object();
        this.resMap = new HashMap<String, Object>();
    }
}
