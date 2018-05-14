package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JPanel;

/**
 * 
 * @author Ibragim Kuliev
 *
 */
public class RgbwLedGroupController extends JPanel {

    // private static final Logger log = LoggerFactory.getLogger(RgbwLedGroupController.class);

    private final List<RgbwSwatchGroup> rgbwSwatchGroups;
    private final List<Consumer<RgbwColorGroups>> changeCallbacks;

    public RgbwLedGroupController() {
        super();
        this.rgbwSwatchGroups = new ArrayList<>();
        this.changeCallbacks = new ArrayList<>();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        initUI();
    }

    private void initUI() {
        // initial setup
        RgbwColorGroups groups = new RgbwColorGroups();
        groups.addGroup(Color.BLACK, 0, 1);
        setColorGroups(groups);
    }

    public RgbwColorGroups getColorGroups() {

        RgbwColorGroups colorGroups = new RgbwColorGroups();
        for (RgbwSwatchGroup swatchGroup : rgbwSwatchGroups) {
            colorGroups.addGroup(swatchGroup.getColor(), swatchGroup.getWhite(), swatchGroup.getGroupSize());
        }
        return colorGroups;
    }

    public void setColorGroups(final RgbwColorGroups colorGroups) {

        clearColorGroups();
        for (RgbwColorGroup group : colorGroups.getGroups()) {
            RgbwSwatchGroup swatchGroup = RgbwSwatchGroup.create();
            swatchGroup.setColor(group.getColor());
            swatchGroup.setWhite(group.getLuminance());
            swatchGroup.setGroupSize(group.getGroupSize());
            appendSwatchGroup(swatchGroup);
        }
        repaint();
    }

    public void addChangeCallback(final Consumer<RgbwColorGroups> callback) {
        changeCallbacks.add(callback);
    }

    private void clearColorGroups() {
        this.removeAll();
        rgbwSwatchGroups.clear();
    }

    private void addSwatchGroup(final int index, final RgbwSwatchGroup swatchGroup) {

        if (index >= rgbwSwatchGroups.size() - 1) {
            rgbwSwatchGroups.add(swatchGroup);  // append
        } else {
            rgbwSwatchGroups.add(index, swatchGroup);  // insert
        }

        if (rgbwSwatchGroups.size() == 0) {
            this.add(swatchGroup);   // if no groups exist yet, insert at index 0
        } else {
            this.add(swatchGroup, index);
        }

        // prevent removal if there's only one group
        if (rgbwSwatchGroups.size() < 2) {
            swatchGroup.setMinusEnabled(false);
        }

        swatchGroup.registerAddCallback(this::addSwatchGroupAfter);
        swatchGroup.registerRemoveCallback(this::removeSwatchGroup);
        swatchGroup.addChangeCallback(event -> fireChangeEvent());

        this.revalidate();
        this.fireChangeEvent();
    }

    private void appendSwatchGroup(final RgbwSwatchGroup swatchGroup) {
        addSwatchGroup(rgbwSwatchGroups.size(), swatchGroup);
    }

    // sourceGroup is the event originator, i.e. the group to insert AFTER
    private void addSwatchGroupAfter(final RgbwSwatchGroup sourceGroup) {
        final int index = rgbwSwatchGroups.indexOf(sourceGroup);
        addSwatchGroup(index + 1, RgbwSwatchGroup.create());
    }

    private void removeSwatchGroup(final RgbwSwatchGroup group) {
        rgbwSwatchGroups.remove(group);
        this.remove(group);
        this.fireChangeEvent();
        this.revalidate();
        this.repaint();
    }

    private void fireChangeEvent() {
        final RgbwColorGroups colorGroups = getColorGroups();
        for (final Consumer<RgbwColorGroups> callback : changeCallbacks) {
            callback.accept(colorGroups);
        }
    }
}
