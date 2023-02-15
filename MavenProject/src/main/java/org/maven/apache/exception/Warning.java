package org.maven.apache.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.MODULE, ElementType.METHOD, ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Warning {

    WarningType value();

    enum WarningType {
        DEBUG,
        IMPROVEMENT,
        ADDITIONAL_FEATURE,
        DELETE_IN_FUTURE
    }

}
