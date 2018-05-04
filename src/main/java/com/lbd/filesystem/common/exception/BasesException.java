package com.lbd.filesystem.common.exception;

/**
 * Created by luozhanghua on 16/4/28.
 */
public class BasesException extends Exception {
    
	private static final long serialVersionUID = 1L;

	private Integer code;
    
    private String msg;
    
    /**
     * 无参构造
     */
    public BasesException() {
        super();
    }
    
    /**
     * 根据message抛出异常
     * @param message
     */
    public BasesException(String message) {
        super(message);
        this.msg = message;
    }
    
    /**
     * 根据code, message抛出异常
     * @param code
     * @param message
     */
    public BasesException(Integer code, String message) {
        super();
        this.msg = message;
        this.code = code;
    }
    
    /**
     * 获取code
     * @return
     */
    public Integer getCode() {
        return this.code;
    }
    
    /**
     * 设置code
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }
    
    /**
     * 获取msg
     * @return
     */
    public String getMsg() {
        return this.msg;
    }
    
    /**
     * 设置Msg
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
