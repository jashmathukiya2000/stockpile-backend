package com.example.auth.commons;

import com.example.auth.commons.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom Annotation for access information
 * Example Usage: @Access(levels = { Authorization.ADMIN,Authorization.USER} )
 * @author TRS
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // on Method level
public @interface Access {

    Role[] levels() default Role.ADMIN;

    String createdBy() default "AUTH";
}
