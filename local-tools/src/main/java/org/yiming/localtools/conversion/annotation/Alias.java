package org.yiming.localtools.conversion.annotation;

import java.lang.annotation.*;

/**
 * 别名
 */
@Target({ ElementType.METHOD,ElementType.FIELD,ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {

    /**
     * （可选） 字段名称
     * @return
     */
    String name() default "";

}
