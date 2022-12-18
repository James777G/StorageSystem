package org.maven.apache.utils;

import java.util.function.Function;
import java.util.function.Predicate;

public class ConditionUtils<R> implements Predicate<R>, Function {


    @Override
    public boolean test(R r) {
        return false;
    }

    @Override
    public Object apply(Object o) {
        return null;
    }
}
