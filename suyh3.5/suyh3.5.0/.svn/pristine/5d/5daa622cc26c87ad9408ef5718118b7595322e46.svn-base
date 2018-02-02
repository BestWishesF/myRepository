package cn.hotol.wechat.domain.json;

import org.springframework.validation.FieldError;

/**
 * 错误信息
 */
public class JSONError {

    private String objectName;

    private String field;

    private String fieldMessage;

    private Object rejectedValue;

    private boolean isBindingFailure;

    private String defaultMessage;

    public JSONError(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public JSONError(
            String objectName, String field, Object rejectedValue,
            boolean bindingFailure, String defaultMessage) {

        this.objectName = objectName;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.isBindingFailure = bindingFailure;
        this.defaultMessage = defaultMessage;
    }

    public JSONError(
            String objectName, String field, String fieldMessage, Object rejectedValue,
            boolean bindingFailure, String defaultMessage) {

        this.objectName = objectName;
        this.field = field;
        this.fieldMessage = fieldMessage;
        this.rejectedValue = rejectedValue;
        this.isBindingFailure = bindingFailure;
        this.defaultMessage = defaultMessage;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }

    public void setFieldMessage(String fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public boolean isBindingFailure() {
        return isBindingFailure;
    }

    public void setBindingFailure(boolean bindingFailure) {
        isBindingFailure = bindingFailure;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public static JSONError wrap(FieldError fieldError) {
        return new JSONError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.isBindingFailure(),
                fieldError.getDefaultMessage());
    }

    public static JSONError wrap(String propertyMessage, FieldError fieldError) {
        return new JSONError(
                fieldError.getObjectName(),
                fieldError.getField(),
                propertyMessage,
                fieldError.getRejectedValue(),
                fieldError.isBindingFailure(),
                fieldError.getDefaultMessage());
    }
}
