package de.fb.arduino_sandbox.view.component;

import static org.junit.Assert.*;
import org.junit.Test;
import de.fb.arduino_sandbox.view.component.dial.IntegerRangeModel;

public class RangeModelTest {

    @Test
    public void testCoarseIncrement() {

        IntegerRangeModel model = new IntegerRangeModel(0, 100, 10, 1, 10);
        assertEquals(10, model.getValue());
        model.coarseIncrement();
        assertEquals(20, model.getValue());
    }

    @Test
    public void testCoarseDecrement() {

        IntegerRangeModel model = new IntegerRangeModel(0, 100, 10, 1, 20);
        assertEquals(20, model.getValue());
        model.coarseDecrement();
        assertEquals(10, model.getValue());
    }

    @Test
    public void testCoarseClamp() {

        IntegerRangeModel model = new IntegerRangeModel(-100, 100, 10, 1, 0);

        for (int i = 0; i < 100; i++) {
            model.coarseDecrement();
        }
        assertEquals(model.getMin(), model.getValue());

        for (int i = 0; i < 100; i++) {
            model.coarseIncrement();
        }
        assertEquals(model.getMax(), model.getValue());
    }


    @Test
    public void testFineIncrement() {

        IntegerRangeModel model = new IntegerRangeModel(0, 100, 10, 1, 10);
        assertEquals(10, model.getValue());
        model.fineIncrement();
        assertEquals(11, model.getValue());
    }

    @Test
    public void testFineDecrement() {

        IntegerRangeModel model = new IntegerRangeModel(0, 100, 10, 1, 10);
        assertEquals(10, model.getValue());
        model.fineDecrement();
        assertEquals(9, model.getValue());
    }

    @Test
    public void testFineClamp() {

        IntegerRangeModel model = new IntegerRangeModel(-10, 10, 10, 1, 0);

        for (int i = 0; i < 100; i++) {
            model.fineDecrement();
        }
        assertEquals(model.getMin(), model.getValue());

        for (int i = 0; i < 100; i++) {
            model.fineIncrement();
        }
        assertEquals(model.getMax(), model.getValue());
    }

}
