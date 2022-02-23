package me.ikevoodoo.plugincore.annotations.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

    String path();
    String redirectPath() default "";
    String permission() default "";
    CommandUser usableBy() default CommandUser.ALL;
    //boolean includeSub() default false;

}
