package io.anshily.base.core;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
public class ServiceException extends RuntimeException {
    public Integer code;
    public Integer getCode(){
        return this.code;
    }
    public void setCode(Integer code){
        this.code = code;
    }
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Integer code) {
        super(Constants.messageMap.get(code));
        this.code = code;
    }
    public ServiceException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
