package com.university.direct_current_motor.adapters.desktop.common.components;

import com.university.direct_current_motor.adapters.desktop.feautures.components.NotFoundScreen;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Objects;

public final class Root extends Component {

    public Root() {
        super();
        this.initUI();
    }

    public void setScreen(final Screen screen) {
        final Pane node = screen.getNode();

        node.prefWidthProperty().bind(this.getNode().widthProperty());
        node.prefHeightProperty().bind(this.getNode().heightProperty());

        super.getNode().getChildren().setAll(node);
    }

    private void initUI() {
        final ObservableList<Node> children = this.getNode().getChildren();
        if (Objects.isNull(children) || children.isEmpty()) {
            this.showNotFoundScreen();
        }
    }

    private void showNotFoundScreen() {
        final InjectionService injectionService = InjectionService.getInjectionService();
        final NotFoundScreen notFoundScreen = injectionService.getBean(NotFoundScreen.class);
        this.setScreen(notFoundScreen);
    }

}
