package me.ikevoodoo.plugincore.annotations.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BaseCommand {

    String name();
    String prefix() default "command";
    boolean autoCompletion() default false;
    CommandUser usableBy() default CommandUser.ALL;

}
