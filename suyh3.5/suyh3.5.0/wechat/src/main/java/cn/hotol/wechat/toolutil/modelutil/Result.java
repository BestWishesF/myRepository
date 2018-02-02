package cn.hotol.wechat.toolutil.modelutil;


import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * login: Hill Pan
 * Date: 8/15/11
 * Time: 10:29 AM
 */
public class Result<T> implements Serializable {

	public static final Result NEED_LOGIN = new Result(true);

	private boolean needLogin;

	private boolean success = true;

	private T value;

	private List<T> list;

	private String errorMsg;

	private String msg;

	private List<ObjectError> objectErrors;

    private Date currentDate = new Date();

    private long tag = new Date().getTime();

    private Result() {
	}

	private Result(T value) {
		this.value = value;
	}

	private Result(T value, String msg) {
		this.value = value;
        this.msg = msg;
	}

	private Result(boolean needLogin) {
		this.needLogin = needLogin;
		this.success = !needLogin;
	}

	private Result(String errorMsg) {
		this.success = false;
		this.errorMsg = errorMsg;
	}
	private Result(List<ObjectError> objectErrors) {
		this.success = false;
		this.objectErrors = objectErrors;
	}

	public T getValue() {
		return value;
	}

	public boolean isNeedLogin() {
		return needLogin;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

    public String getMsg() {
        return msg;
    }

    public List<ObjectError> getObjectErrors() {
		return objectErrors;
	}

	public void setObjectErrors(List<ObjectError> objectErrors) {
		this.objectErrors = objectErrors;
	}

	public static <T> Result<T> success() {
		return new Result<T>();
	}

	public static <T> Result<T> success(T t) {
		return new Result<T>(t);
	}

	public static <T> Result<T> success(T t, String msg) {
		return new Result<T>(t, msg);
	}

	public static <T> Result<T> failure(String errorMsg) {
		return new Result<T>(errorMsg);
	}

	public static <T> Result<T> failure(List<ObjectError> objectErrors) {

		return new Result<T>(objectErrors);
	}

    public Date getCurrentDate() {
        return currentDate;
    }

    public long getTag() {
        return tag;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
