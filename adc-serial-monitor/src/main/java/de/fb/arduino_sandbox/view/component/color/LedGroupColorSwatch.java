package de.fb.arduino_sandbox.view.component.color;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class LedGroupColorSwatch extends JPanel {

    private final List<RgbwSwatchGroup> rgbwSwatchGroups;

    public LedGroupColorSwatch() {
        super();
        this.rgbwSwatchGroups = new ArrayList<>();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        initUI();
    }

    private void initUI() {
        // test
        for (int i = 0; i < 1; i++) {
            addSwatchGroup(i);
        }
    }

    private void addSwatchGroup(final int index) {

        final RgbwSwatchGroup swatchGroup = RgbwSwatchGroup.create();

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
        this.revalidate();
    }

    // sourceGroup is the event originator, i.e. the group to insert AFTER
    private void addSwatchGroupAfter(final RgbwSwatchGroup sourceGroup) {
        final int index = rgbwSwatchGroups.indexOf(sourceGroup);
        addSwatchGroup(index + 1);
    }

    private void removeSwatchGroup(final RgbwSwatchGroup group) {
        rgbwSwatchGroups.remove(group);
        this.remove(group);
        this.revalidate();
        this.repaint();
    }
}
