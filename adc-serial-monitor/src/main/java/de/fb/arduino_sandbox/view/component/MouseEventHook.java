package de.fb.arduino_sandbox.view.component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class MouseEventHook implements MouseListener {

    final Consumer<MouseEvent> mouseClickedCallback;
    final Consumer<MouseEvent> mousePressedCallback;
    final Consumer<MouseEvent> mouseReleasedCallback;
    final Consumer<MouseEvent> mouseEnteredCallback;
    final Consumer<MouseEvent> mouseExitedCallback;

    public MouseEventHook(final Consumer<MouseEvent> mouseClickedCallback,
        final Consumer<MouseEvent> mousePressedCallback,
        final Consumer<MouseEvent> mouseReleasedCallback,
        final Consumer<MouseEvent> mouseEnteredCallback,
        final Consumer<MouseEvent> mouseExitedCallback) {

        this.mouseClickedCallback = mouseClickedCallback;
        this.mousePressedCallback = mousePressedCallback;
        this.mouseReleasedCallback = mouseReleasedCallback;
        this.mouseEnteredCallback = mouseEnteredCallback;
        this.mouseExitedCallback = mouseExitedCallback;
    }

    @Override
    public void mouseClicked(final MouseEvent event) {
        if (mouseClickedCallback != null) {
            mouseClickedCallback.accept(event);
        }
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        if (mousePressedCallback != null) {
            mousePressedCallback.accept(event);
        }
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        if (mouseReleasedCallback != null) {
            mouseReleasedCallback.accept(event);
        }
    }

    @Override
    public void mouseEntered(final MouseEvent event) {
        if (mouseEnteredCallback != null) {
            mouseEnteredCallback.accept(event);
        }
    }

    @Override
    public void mouseExited(final MouseEvent event) {
        if (mouseExitedCallback != null) {
            mouseExitedCallback.accept(event);
        }
    }

}
