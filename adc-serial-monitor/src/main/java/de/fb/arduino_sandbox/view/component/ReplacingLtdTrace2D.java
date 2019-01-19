package de.fb.arduino_sandbox.view.component;

import info.monitorenter.gui.chart.ITracePoint2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import java.util.Iterator;

/** fixes the original replacing trace's inability to specify the trace name */
public class ReplacingLtdTrace2D extends Trace2DLtd {

    /** Generated <code>serialVersionUID</code>. */
    private static final long serialVersionUID = -6048361222161598032L;

    /**
     * Constructs a <code>Trace2DLtdReplacing</code> with a default buffer size of 100.
     */
    public ReplacingLtdTrace2D() {
        this(100);
    }

    /**
     * Constructs an instance with a buffer size of bufsize.
     * <p>
     * 
     * @param bufsize
     *        the maximum amount of points that will be displayed.
     */
    public ReplacingLtdTrace2D(final int bufsize) {
        super(bufsize);
    }

    public ReplacingLtdTrace2D(final int bufsize, final String name) {
        super(bufsize, name);
    }

    /**
     * @see ATrace2D#addPointInternal(info.monitorenter.gui.chart.ITracePoint2D)
     */
    @Override
    protected boolean addPointInternal(final ITracePoint2D p) {
        boolean result = false;
        boolean located = false;
        ITracePoint2D tmp;
        double tmpx;
        double tmpy;
        Iterator<ITracePoint2D> it = this.m_buffer.iteratorF2L();
        while (it.hasNext()) {

            tmp = it.next();
            tmpx = tmp.getX();
            if (tmpx == p.getX()) {
                tmpy = p.getY();
                if (tmpy == tmp.getY()) {
                    // performs bound checks and fires property changes
                    tmp.setLocation(tmpx, tmpy);
                    // don't need bound checks of calling addPoint.
                    located = true;
                }
            }
        }
        if (!located) {
            // no matching point was found and shifted:
            result = super.addPointInternal(p);
        }
        return result;
    }
}
