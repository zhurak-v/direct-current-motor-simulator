package com.university.direct_current_motor.adapters.desktop.services;

import com.university.direct_current_motor.adapters.desktop.common.components.Component;
import com.university.direct_current_motor.core.ports.services.InjectionService;

public abstract class AbstractComponentFactoryImpl<T extends Component>
        implements ComponentFactory<T>
{

    protected final InjectionService injectionService;

    protected AbstractComponentFactoryImpl() {
        this.injectionService = InjectionService.getInjectionService();
    }

}
