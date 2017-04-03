package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.impl.patcher.property.SceneAccessor;
import com.netopyr.reduxfx.impl.patcher.property.StageShowingAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javaslang.collection.Array;
import javaslang.collection.Map;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.CLOSE_REQUEST;

@SuppressWarnings("unused")
public class StageBuilder<BUILDER extends StageBuilder<BUILDER>> extends Builder<BUILDER> {

    private static final String SCENE = "scene";
    private static final String SHOWING = "showing";
    private static final String TITLE = "title";

    public StageBuilder(
            Class<?> nodeClass,
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new StageBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER scene(SceneBuilder<?> value) {
        Accessors.registerAccessor(Stage.class, "scene", SceneAccessor::new);
        return child(SCENE, value);
    }

    public BUILDER showing(boolean value) {
        Accessors.registerAccessor(Stage.class, "showing", StageShowingAccessor::new);
        return property(SHOWING, value);
    }

    public BUILDER title(String value) {
        return property(TITLE, value);
    }


    public BUILDER onCloseRequest(VEventHandler<WindowEvent> eventHandler) {
        return onEvent(CLOSE_REQUEST, eventHandler);
    }

}
