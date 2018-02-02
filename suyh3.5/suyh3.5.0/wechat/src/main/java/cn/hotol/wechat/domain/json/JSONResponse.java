package cn.hotol.wechat.domain.json;

import cn.hotol.wechat.toolutil.modelutil.BeanMessagesUtil;
import cn.hotol.wechat.toolutil.modelutil.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 服务器返回对象
 * 所有服务器处理返回的统一对象
 */
public class JSONResponse<T> {
    //成功、失败标志
    private boolean success = false;
    //错误信息
    private ArrayList<JSONError> errors = null;
    //成功时返回的对象
    private T value = null;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setErrors(ArrayList<JSONError> errors) {
        this.errors = errors;
    }

    public ArrayList<JSONError> getErrors() {
        return errors;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <T> JSONResponse<T> wrap(Result<T> result) {

        if (result.isSuccess()) {
            JSONResponse<T> jsonResponse = success(result.getValue());
            jsonResponse.setValue(result.getValue());
            return jsonResponse;
        } else {
            return failure(result.getErrorMsg());
        }
    }

    public static <T> JSONResponse<T> success(T t) {

        JSONResponse<T> ret = new JSONResponse<T>();

        ret.setSuccess(true);

        ret.setValue(t);

        return ret;
    }

    public static <T> JSONResponse<T> failure(String msg) {

        ArrayList<JSONError> errors = new ArrayList<JSONError>();

        errors.add(new JSONError(msg));

        JSONResponse<T> ret = new JSONResponse<T>();

        ret.setSuccess(false);

        ret.setErrors(errors);

        return ret;
    }

    public static <T> JSONResponse<T> failure(BindingResult result) {

        JSONResponse<T> ret = new JSONResponse<T>();

        ret.setSuccess(false);

        ret.setErrors(createJSONErrors(result));

        return ret;
    }

    private static ArrayList<JSONError> createJSONErrors(BindingResult result) {

        ArrayList<JSONError> errors = new ArrayList<JSONError>();

        ResourceBundle classMessages =
                BeanMessagesUtil.getResourceBundle(result.getTarget());

        List<FieldError> fieldErrorList = result.getFieldErrors();

        for (FieldError fieldError : fieldErrorList) {
            errors.add(JSONError.wrap(classMessages.getString(fieldError.getField()), fieldError));
        }

        return errors;
    }
}
