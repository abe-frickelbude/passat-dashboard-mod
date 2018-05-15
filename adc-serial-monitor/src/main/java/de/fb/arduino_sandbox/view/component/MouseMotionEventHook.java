package de.fb.arduino_sandbox.view.component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.Consumer;

public class MouseMotionEventHook implements MouseMotionListener {

    final Consumer<MouseEvent> mouseMovedCallback;
    final Consumer<MouseEvent> mouseDraggedCallback;

    public MouseMotionEventHook(final Consumer<MouseEvent> mouseMovedCallback, final Consumer<MouseEvent> mouseDraggedCallback) {
        this.mouseMovedCallback = mouseMovedCallback;
        this.mouseDraggedCallback = mouseDraggedCallback;
    }

    @Override
    public void mouseMoved(final MouseEvent event) {
        if (mouseMovedCallback != null) {
            mouseMovedCallback.accept(event);
        }
    }

    @Override
    public void mouseDragged(final MouseEvent event) {
        if (mouseDraggedCallback != null) {
            mouseDraggedCallback.accept(event);
        }
    }
}
