package me.ikevoodoo.plugincore.annotations.commands;

import org.bukkit.permissions.PermissionDefault;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    String name();
    String prefix() default "command";
    String description() default "";
    String usage() default "";
    String permission() default "";
    String[] aliases() default "";
    CommandUser usableBy() default CommandUser.ALL;
    PermissionDefault defaultPerm() default PermissionDefault.TRUE;


}