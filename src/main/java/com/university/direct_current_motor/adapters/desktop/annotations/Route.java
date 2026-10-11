package com.university.direct_current_motor.adapters.desktop.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String name();
}
