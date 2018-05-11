package de.fb.arduino_sandbox.view.component;

import static org.junit.Assert.*;
import org.junit.Test;
import de.fb.arduino_sandbox.view.component.color.RangeModel;

public class RangeModelTest {


    @Test
    public void testIncrement() {

        RangeModel model = new RangeModel(0, 100, 1, 10);
        assertEquals(10, model.getValue());
        model.increment();
        assertEquals(11, model.getValue());
    }

    @Test
    public void testDecrement() {

        RangeModel model = new RangeModel(0, 100, 1, 10);
        assertEquals(10, model.getValue());
        model.decrement();
        assertEquals(9, model.getValue());
    }

    @Test
    public void testClamp() {

        RangeModel model = new RangeModel(-10, 10, 1, 0);

        for (int i = 0; i < 100; i++) {
            model.decrement();
        }
        assertEquals(model.getMin(), model.getValue());

        for (int i = 0; i < 100; i++) {
            model.increment();
        }

        assertEquals(model.getMax(), model.getValue());
    }

}
