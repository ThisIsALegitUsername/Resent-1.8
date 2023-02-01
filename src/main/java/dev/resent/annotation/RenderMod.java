package dev.resent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.resent.module.base.Category;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RenderMod {
    String name();
    Category category();
    int x();
    int y();
    boolean hasSetting() default false;
}
