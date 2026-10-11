package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.annotations.Route;
import com.university.direct_current_motor.adapters.desktop.common.components.Box;
import com.university.direct_current_motor.adapters.desktop.common.components.Screen;
import com.university.direct_current_motor.adapters.desktop.common.components.Typography;

@Route(name = "not-found-screen")
public final class NotFoundScreen extends Screen {

    public NotFoundScreen() {
        super();
        this.addNotFoundChildren();
    }

    public void addNotFoundChildren() {
        final Box box = new Box();
        final Typography title = new Typography("Resource is not found");

        box.addChildren(title);

        this.addChildren(box);
    }

}
