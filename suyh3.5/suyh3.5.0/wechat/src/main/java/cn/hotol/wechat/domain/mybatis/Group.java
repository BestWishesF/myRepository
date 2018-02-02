package cn.hotol.wechat.domain.mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * login: Hill Pan
 * Date: 4/18/13
 * Time: 9:48 AM
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Group {

	Class<?>[] value() default {};
}
