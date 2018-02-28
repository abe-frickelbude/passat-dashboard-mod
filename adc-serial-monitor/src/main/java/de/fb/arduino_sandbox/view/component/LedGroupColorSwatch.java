package de.fb.arduino_sandbox.view.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class LedGroupColorSwatch extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(LedGroupColorSwatch.class);

    private final int numRows;
    private final int numColumns;

    private final FormLayout layout;
    private List<RgbwSwatchGroup> rgbwSwatchGroups;

    public LedGroupColorSwatch(final int numRows, final int numColumns) {

        super();

        this.numRows = numRows;
        this.numColumns = numColumns;
        this.rgbwSwatchGroups = new ArrayList<>();

        this.layout = new FormLayout(new ColumnSpec[] {},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC
        });
        setLayout(layout);
        initUI();
    }

    private void initUI() {

        setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new LineBorder(new Color(187, 187, 187))));

        layout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
        layout.appendColumn(ColumnSpec.decode("48px"));
        // layout.appendColumn(FormSpecs.DEFAULT_COLSPEC);
        // layout.appendColumn(ColumnSpec.decode("48px:grow"));

        // layout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
        // layout.appendColumn(ColumnSpec.decode("max(35dlu;default)"));

        final RgbwSwatchGroup swatchGroup = RgbwSwatchGroup.create();
        rgbwSwatchGroups.add(swatchGroup);

        this.add(swatchGroup.getColorSwatch(), "2, 2, center, default");
        this.add(swatchGroup.getWhiteSwatch(), "2, 4, center, default");
        this.add(swatchGroup.getGroupSizeSpinner(), "2,6");

        layout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
        layout.appendColumn(ColumnSpec.decode("48px"));

        final RgbwSwatchGroup swatchGroup2 = RgbwSwatchGroup.create();
        rgbwSwatchGroups.add(swatchGroup2);

        this.add(swatchGroup2.getColorSwatch(), "4, 2, center, default");
        this.add(swatchGroup2.getWhiteSwatch(), "4, 4, center, default");
        this.add(swatchGroup2.getGroupSizeSpinner(), "4,6");

        // this.add(new JLabel("1"), "2,2, center, default");
        // this.add(new JColorSwatch.Builder().swatchSize(48).build(), "2,4, center, default");
        // this.add(new JColorSwatch.Builder().swatchSize(48).build(), "2,6, center, default");
        // this.add(new JSpinner(), "2,8, fill, default");
        //
        // this.add(new JLabel("2"), "4,2, center, default");
        // this.add(new JColorSwatch.Builder().swatchSize(48).build(), "4,4, center, default");
        // this.add(new JColorSwatch.Builder().swatchSize(48).build(), "4,6, center, default");
        // this.add(new JSpinner(), "4,8, fill, default");

        // final FormLayout layout = new FormLayout(new ColumnSpec[] {}, new RowSpec[] {});
        // layout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
        // layout.appendRow(FormSpecs.RELATED_GAP_ROWSPEC);

        // this.add(filterControlPanel, "2, 12, fill, default");

        // grid columns and rows have to be completely specified first!
        // for (int i = 1; i <= numColumns; i++) {
        // layout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
        // layout.appendColumn(FormSpecs.DEFAULT_COLSPEC);
        // }
        //
        // for (int i = 1; i <= numRows; i++) {
        // layout.appendRow(FormSpecs.RELATED_GAP_ROWSPEC);
        // layout.appendRow(FormSpecs.DEFAULT_ROWSPEC);
        // }
        //
        // layout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
        // layout.appendRow(FormSpecs.RELATED_GAP_ROWSPEC);
        // setLayout(layout);
        //
        // // add swatches
        // for (int i = 1; i <= numRows; i++) {
        // for (int j = 1; j <= numColumns; j++) {
        //
        // String constraints = MessageFormat.format("{0}, {1}, default, default", (j * 2), (i * 2));
        // // log.info("Constraints for column {}, row{} : {}", j, i, constraints);
        //
        // JLabel label = new JLabel(String.valueOf(i * (j - 1)));
        // JColorSwatch swatch = new JColorSwatch.Builder().swatchSize(32).build();
        //
        // this.add(label, constraints);
        // this.add(swatch, constraints);
        //
        // }
        // }
    }

}
