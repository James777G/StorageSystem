package org.maven.apache.service.staff;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service("staffStrategies")
public class GeneralStaffStrategiesProvider implements GeneralStaffStrategies{

    @Resource
    private AbstractStaffStrategy appPageSynchronizingStaffStrategy;

    @Override
    public void doStrategies() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        appPageSynchronizingStaffStrategy.doStrategy();
    }
}
