package de.fb.arduino_sandbox.view.component.color;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class LedGroupColorSwatch extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(LedGroupColorSwatch.class);

    private static final String FILL_CONSTRAINT_TEMPLATE = "{0}, {1}, fill, fill";
    private static final String CENTER_CONSTRAINT_TEMPLATE = "{0}, {1}, center, center";

    private final FormLayout layout;
    private List<RgbwSwatchGroup> rgbwSwatchGroups;

    public LedGroupColorSwatch() {

        super();
        this.rgbwSwatchGroups = new ArrayList<>();

        this.layout = new FormLayout(new ColumnSpec[] {},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("48px"), // color swatch
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("48px"), // white swatch
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC, // count spinner
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC, // remove button
                FormSpecs.RELATED_GAP_ROWSPEC
        });
        setLayout(layout);
        initUI();
    }

    private void initUI() {

        // test
        for (int i = 0; i < 10; i++) {
            appendSwatchGroup();
        }
    }

    private String makeFillConstraints(final int column, final int row) {
        return MessageFormat.format(FILL_CONSTRAINT_TEMPLATE, column, row);
    }

    private String makeCenterConstraints(final int column, final int row) {
        return MessageFormat.format(CENTER_CONSTRAINT_TEMPLATE, column, row);
    }

    private void appendSwatchGroup() {

        layout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
        layout.appendColumn(ColumnSpec.decode("48px"));

        final RgbwSwatchGroup swatchGroup = RgbwSwatchGroup.create();
        rgbwSwatchGroups.add(swatchGroup);

        // calculate the column index based on the number of existing groups
        // note: stuff always added to EVEN columns and EVEN rows, as the ODD ones contain the gaps
        // row 2 - color swatch
        // row 4 - white swatch
        // row 6 - spinner
        // row 8 - remove button

        final int columnIndex = 2 * (rgbwSwatchGroups.size());
        this.add(swatchGroup.getColorSwatch(), makeFillConstraints(columnIndex, 2));
        this.add(swatchGroup.getWhiteSwatch(), makeFillConstraints(columnIndex, 4));
        this.add(swatchGroup.getGroupSizeSpinner(), makeFillConstraints(columnIndex, 6));

        this.add(swatchGroup.getPlusMinusButtons(), makeCenterConstraints(columnIndex, 8));

        // prevent removal if there's only one group
        if (rgbwSwatchGroups.size() < 2) {
            swatchGroup.getPlusMinusButtons().getButton(0).setEnabled(false);
        }

        swatchGroup.registerRemoveCallback(this::removeSwatchGroup);
        swatchGroup.registerAddCallback(this::addSwatchGroup);
    }

    private void addSwatchGroup(final RgbwSwatchGroup group) {

        // +1 offset because the column indexing is 1-based
        final int columnIndex = 2 * (rgbwSwatchGroups.indexOf(group) + 1);

        log.info("Adding at column {}", columnIndex);

        // WIP
        // layout.insertColumn(columnIndex + 1, FormSpecs.RELATED_GAP_COLSPEC);
        // layout.insertColumn(columnIndex + 2, ColumnSpec.decode("48px"));
        //
        // final RgbwSwatchGroup swatchGroup = RgbwSwatchGroup.create();
        // rgbwSwatchGroups.add(swatchGroup);
        //
        // // calculate the column index based on the number of existing groups
        // // note: stuff always added to EVEN columns and EVEN rows, as the ODD ones contain the gaps
        // // row 2 - color swatch
        // // row 4 - white swatch
        // // row 6 - spinner
        // // row 8 - remove button
        //
        // this.add(swatchGroup.getColorSwatch(), makeFillConstraints(columnIndex + 2, 2));
        // this.add(swatchGroup.getWhiteSwatch(), makeFillConstraints(columnIndex + 2, 4));
        // this.add(swatchGroup.getGroupSizeSpinner(), makeFillConstraints(columnIndex + 2, 6));
        // this.add(swatchGroup.getPlusMinusButtons(), makeCenterConstraints(columnIndex + 2, 8));

        this.revalidate();
        this.repaint();
    }

    private void removeSwatchGroup(final RgbwSwatchGroup group) {

        // +1 offset because the column indexing is 1-based
        final int columnIndex = 2 * (rgbwSwatchGroups.indexOf(group) + 1);

        // components have to be removed first otherwise the layout manager will complain when trying to remove the columns
        rgbwSwatchGroups.remove(group);
        this.remove(group.getColorSwatch());
        this.remove(group.getWhiteSwatch());
        this.remove(group.getGroupSizeSpinner());
        this.remove(group.getPlusMinusButtons());

        /*
         * Note: the FormLayout manager reflows the columns accordingly, i.e. indices are shifted etc,
         * so we only need to remove the affected columns in the correct order and everything else will be done
         * automatically.
         */
        layout.removeColumn(columnIndex); // group content
        layout.removeColumn(columnIndex - 1); // gap
        this.revalidate();
        this.repaint();
    }
}
