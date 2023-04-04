package dev.resent.annotation;

import dev.resent.module.base.Mod.Category;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String name() default "No name set";
    String description() default "No description set.";
    Category category() default Category.MISC;
    boolean hasSetting() default false;
}
