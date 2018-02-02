package cn.hotol.app.datasource;

/**
 * Created by lizhun on 2017/3/21.
 */

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceChange {
    boolean slave() default false;
}
