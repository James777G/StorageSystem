package org.maven.apache.exception;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.MODULE, ElementType.METHOD, ElementType.PACKAGE,ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Warning {

    enum WarningType{
        DEBUG,
        IMPROVEMENT,
        ADDITIONAL_FEATURE,
        DELETE_IN_FUTURE
    }

    WarningType value();

}
