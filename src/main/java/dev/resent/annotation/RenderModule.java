package dev.resent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.resent.module.base.Mod.Category;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RenderModule {
    String name();
    Category category();
    int x();
    int y();
    boolean hasSetting() default false;
}
