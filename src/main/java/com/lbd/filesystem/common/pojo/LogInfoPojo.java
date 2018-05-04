package com.lbd.filesystem.common.pojo;

/**
 * Created by luozhanghua on 2016/9/12 0012.
 */
public class LogInfoPojo {

    private String log_record_date_string;

    private String client_ip;

    private String req_url;

    private String req_method;

    private String req_params;

    private String exception_code;

    private String exception_cause;

    private String exception_info;

    private String exception_method;

    public String getLog_record_date_string() {
        return log_record_date_string;
    }

    public void setLog_record_date_string(String log_record_date_string) {
        this.log_record_date_string = log_record_date_string;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getReq_url() {
        return req_url;
    }

    public void setReq_url(String req_url) {
        this.req_url = req_url;
    }

    public String getReq_method() {
        return req_method;
    }

    public void setReq_method(String req_method) {
        this.req_method = req_method;
    }

    public String getReq_params() {
        return req_params;
    }

    public void setReq_params(String req_params) {
        this.req_params = req_params;
    }

    public String getException_code() {
        return exception_code;
    }

    public void setException_code(String exception_code) {
        this.exception_code = exception_code;
    }

    public String getException_cause() {
        return exception_cause;
    }

    public void setException_cause(String exception_cause) {
        this.exception_cause = exception_cause;
    }

    public String getException_info() {
        return exception_info;
    }

    public void setException_info(String exception_info) {
        this.exception_info = exception_info;
    }

    public String getException_method() {
        return exception_method;
    }

    public void setException_method(String exception_method) {
        this.exception_method = exception_method;
    }
}
