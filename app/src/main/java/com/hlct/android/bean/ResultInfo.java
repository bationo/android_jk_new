package com.hlct.android.bean;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/7/21
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description : 用于接受服务器返回数据的类
 */

public class ResultInfo implements java.io.Serializable {
    private static final long serialVersionUID = 7981560250804078637L;
    public static final String CODE_SUCCESS = "1";
    public static final String CODE_ERROR = "2";
    private String code;
    private String message;
    //    private PdaLoginMessage pdaLogMessge;
    /**
     * 数据信息
     */
    private String text;
    private JSONArray jsonArray;
    private JSONObject jsonObject;

    //    public PdaLoginMessage getPdaLogMessge() {
    //        return pdaLogMessge;
    //    }
    //
    //    public void setPdaLogMessge(PdaLoginMessage pdaLogMessge) {
    //        this.pdaLogMessge = pdaLogMessge;
    //    }

    public ResultInfo() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

}
