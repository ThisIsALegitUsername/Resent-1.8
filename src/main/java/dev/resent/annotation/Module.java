package dev.resent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.resent.module.base.Category;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String name() default "placeholder";
    Category category() default Category.MISC;
    boolean hasSetting() default false;
}
