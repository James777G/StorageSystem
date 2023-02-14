package org.maven.apache.service.staff;

import java.lang.reflect.InvocationTargetException;

public abstract sealed class AbstractStaffStrategy permits AppPageSynchronizingStrategy{

    public abstract void doStrategy() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;
}
