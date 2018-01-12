package de.fb.processing.arduino;

import java.io.File;
import processing.core.PApplet;
import processing.core.PFont;
import processing.serial.Serial;

public class AdcMonitor extends PApplet {

    // The serial port
    Serial arduinoPort;
    Thread serialThread;

    int testMode = 1;

    // line delimiter
    int lf = 10;

    int viewHeight = 800;
    int viewWidth = 1200;
    float viewAreaX = 90;
    float viewAreaY = 90;

    int backLight = color(30, 30, 30);
    int titleColor = color(255, 255, 255);

    int titleSize = 18;
    int titleY = 20;

    int axisColor = color(150, 150, 150);
    float axisStroke = 1;

    int gridTextColor = color(200, 200, 200);
    float leftBorder = 0.035f;
    float gridTextOffset = 7;
    int gridTextSize = 10;

    int gridColor = color(100, 100, 100);
    float gridStart = -0.005f;

    int lineColor = color(150, 180, 255);
    int filteredLineColor = color(255, 255, 0);
    int scaledLineColor = color(150, 255, 150);

    float lineStroke = 0.01f;

    String unit = "";

    PFont font;

    float changeThreshold = 0.001f;
    float lightThreshold = 0.5f;

    float ADC_SCALE_FACTOR = 4.9f;
    float ADC_THRESHOLD = 2.042f;

    float viewRatio = (viewWidth * viewAreaX / 100.0f) / (viewHeight * viewAreaY / 100.0f);

    // here we store the data
    int dataSetSize = 1000;
    DataSet dataSet = new DataSet(dataSetSize);

    // our Kalman Filter
    KalmanFilter filter = new KalmanFilter(0.25f, 256, 100, 0);
    DataSet filteredDataSet = new DataSet(dataSetSize);

    // scaled data
    DataSet scaledDataSet = new DataSet(dataSetSize);

    // data to store files
    String filePath = "/temp";
    int fileNumber = 0;
    String fileBaseName = "ATMEGA328P_KALMAN";
    String fileExtension = "png";
    int fileWait = 0;
    int fileMaxWait = 10;

    public static void main(final String[] args) {
        PApplet.main(AdcMonitor.class);
    }

    @Override
    public void settings() {
        size(viewWidth, viewHeight, P3D);
    }

    @Override
    public void setup() {

        smooth();
        // noStroke();

        font = loadFont("LucidaGrande-48.vlw");

        // List all the available serial ports:
        if (testMode == 0) {
            println(Serial.list());
            arduinoPort = new Serial(this, Serial.list()[0], 57600);

            serialThread = new Thread(new Runnable() {

                @Override
                public void run() {

                    float prevFilteredValue = 0;

                    while (true) {
                        String text = arduinoPort.readStringUntil(lf);

                        if ((text != null) && (text.length() > 2)) {
                            text = text.substring(0, text.length() - 2);
                            // println(text);

                            try {

                                int adcValue = Integer.parseInt(text);

                                dataSet.addSample(adcValue);
                                float filteredValue = filter.addSample(adcValue);
                                filteredDataSet.addSample(filteredValue);

                                if (abs(filteredValue - prevFilteredValue) > ADC_THRESHOLD) {
                                    prevFilteredValue = ceil(filteredValue);

                                    print("Current filtered adc value: " + filteredValue + ", approx. "
                                        + ceil(filteredValue * ADC_SCALE_FACTOR) + " mv");
                                    println(" Prev. filtered value: " + prevFilteredValue + ", approx. "
                                        + ceil(filteredValue * ADC_SCALE_FACTOR) + " mv");

                                    scaledDataSet.addSample(ceil((filteredValue * ADC_SCALE_FACTOR) / 10.0f));
                                } else {
                                    // println("Current filtered adc value: " + filteredValue + ", approx. " +
                                    // ceil(filteredValue * ADC_SCALE_FACTOR) + " mv -> rejected");

                                    // repeat last sample
                                    scaledDataSet.addSample(ceil((prevFilteredValue * ADC_SCALE_FACTOR) / 10.0f));
                                }

                                // dataSet.addSample( (adcValue * ADC_SCALE_FACTOR) / 100.0 );
                                // float filtered_value = filter.addSample((adcValue * ADC_SCALE_FACTOR) / 100.0);
                                // filteredDataSet.addSample(filtered_value);

                                // dataSet.addSample(adcValue);
                                // float filtered_value = filter.addSample(adcValue);
                                // filteredDataSet.addSample(filtered_value);
                                // scaledDataSet.addSample( int(ceil(filtered_value) * ADC_SCALE_FACTOR) );

                            } catch (NumberFormatException ex) {
                                println("Could not understand '" + text + "'.");
                            }

                        }

                        // if ((text!=null) && (text.length()>2)) {
                        // //println(text);
                        //
                        // StringTokenizer tokenizer = new StringTokenizer(text," \n");
                        // if (tokenizer.countTokens()==3){
                        // String xv = tokenizer.nextToken();
                        // String yv = tokenizer.nextToken();
                        // String zv = tokenizer.nextToken();
                        // //println(c+","+d+" -> "+v);
                        // try {
                        // int x =Integer.parseInt(xv);
                        // int y = Integer.parseInt(yv);
                        // int z = Integer.parseInt(zv);
                        //
                        // dataSet.addSample(x*0.018);
                        // float filtered_x = filter.addSample(x);
                        // filteredDataSet.addSample(filtered_x*0.018);
                        //
                        // //println(filter);
                        // }
                        // catch (NumberFormatException e) {
                        // //did not work
                        // println("Could not understand '"+text+"'.");
                        // }
                        // }
                        // }

                    }
                }
            });
        } else {

            serialThread = new Thread(new Runnable() {

                int number = 0;

                @Override
                public void run() {
                    while (true) {
                        dataSet.addSample(sin(number++ / (8 * PI)));
                        delay(50);
                    }
                }
            });
        }

        serialThread.start();
    }

    @Override
    public void draw() {
        background(backLight);

        synchronized (dataSet) {

            // can this be done simpler?
            float border = min((viewWidth * (100 - viewAreaX) / 100.0f) / 2.0f, (viewHeight * (100 - viewAreaY) / 100.0f) / 2.0f);
            float left = (int) (border) + viewWidth * leftBorder;
            float right = viewWidth - border;
            float bottom = (int) (border);
            float top = viewHeight - border;

            float arrowLong = bottom * 0.5f;
            float arrowShort = bottom * 0.2f;

            stroke(axisColor);
            strokeWeight(axisStroke);

            textMode(MODEL);
            textFont(font, titleSize);
            textAlign(CENTER);
            fill(titleColor);
            StringBuffer titleBuffer = new StringBuffer("LIS302DL & Kalman Filter (p=");
            titleBuffer.append(filter.getP());
            titleBuffer.append(", q=");
            titleBuffer.append(filter.getQ());
            titleBuffer.append(", r=");
            titleBuffer.append(filter.getR());
            titleBuffer.append(", k=");
            titleBuffer.append(filter.getK());
            titleBuffer.append(")");
            text(titleBuffer.toString(), viewWidth / 2, titleY);

            // draw the axis
            // viewport is mirrored – 0 is at top
            line(left, bottom, left, top);
            line(left, bottom, left + arrowShort, bottom + arrowLong);
            line(left, bottom, left - arrowShort, bottom + arrowLong);
            line(left, top, right, top);
            line(right, top, right - arrowLong, top + arrowShort);
            line(right, top, right - arrowLong, top - arrowShort);

            // float mi = -1.1;//dataSet.getMin();
            // float ma = 1.1;//dataSet.getMax();

            float mi = 0;
            float ma = dataSet.getMax();
            // float mi = 0;
            // float ma = 350;

            float span = ma - mi;
            double magnitude = round((float) Math.log10(span));
            float factor = (float) Math.pow(10, 1 - magnitude);
            float lower = round(mi * factor - 1.0f) / factor;
            float upper = round(ma * factor + 1.0f) / factor;
            int steps = round(span * factor);
            int textSteps = 1;
            if (steps > 10) {
                textSteps = round((steps + 1.5f) / 10.0f);
            }

            // println(mi+" ("+lower+") - "+ma+" ("+upper+") = "+span+" ("+steps+" / "+textSteps+") @
            // "+magnitude+","+factor);

            float stepLength = (upper - lower) / steps;

            // Y-axis texts
            stroke(gridTextColor);
            textFont(font, gridTextSize);
            textAlign(RIGHT);

            // viewport is not normalized yet - we have to doit ourself
            // draw the grid
            for (int i = 1; i < steps; i++) {
                // we use ints to produce 'nice numbers'
                int valueText = (int) ((lower + (stepLength * i)) * factor);
                float value = valueText / factor;
                // normalize between top & bottom
                float pos = map(value, lower, upper, top, bottom);

                if ((i % textSteps) == 0) {
                    fill(gridTextColor);
                    text(value + unit, left - gridTextOffset, pos);
                }
                stroke(gridColor);
                line(left + gridStart, pos, right, pos);
            }

            // prepare the viewport so that it is 0-1 with 0,0 at bottom
            rotateX(PI);
            translate(0, -viewHeight);
            translate(left, bottom);
            scale(right - left, top - bottom);

            // draw the actual data
            float sampleSteps = 1.0f / dataSet.getNumberOfSamples();
            for (int i = 1; i < dataSet.getNumberOfSamples(); i++) {
                // normal data
                float sample = norm(dataSet.getSample(i), lower, upper);
                float previous = norm(dataSet.getSample(i - 1), lower, upper);

                float lineLeft = (i - 1) * sampleSteps;
                float lineRight = i * sampleSteps;

                stroke(lineColor);
                strokeWeight(lineStroke);
                line(lineLeft, previous, lineRight, sample);

                // filtered data
                float filteredSample = norm(filteredDataSet.getSample(i), lower, upper);
                float filteredPrevious = norm(filteredDataSet.getSample(i - 1), lower, upper);

                float filteredLineLeft = (i - 1) * sampleSteps;
                float filteredLineRight = i * sampleSteps;

                stroke(filteredLineColor);
                strokeWeight(lineStroke);
                line(filteredLineLeft, filteredPrevious, filteredLineRight, filteredSample);

                // scaled data
                float scaledSample = norm(scaledDataSet.getSample(i), lower, upper);
                float scaledPrevious = norm(scaledDataSet.getSample(i - 1), lower, upper);

                float scaledLineLeft = (i - 1) * sampleSteps;
                float scaledLineRight = i * sampleSteps;

                stroke(scaledLineColor);
                strokeWeight(lineStroke);
                line(scaledLineLeft, scaledPrevious, scaledLineRight, scaledSample);

                // show current scaled value
                // text(scaledDataSet.getSample(i),scaledLineLeft,scaledSample);

            }

        }

        if (keyPressed) {
            if (key == 's' || key == 'S') {
                if (fileWait == 0) {
                    saveImage();
                    fileWait++;
                } else {
                    fileWait++;
                    if (fileWait > fileMaxWait) {
                        fileWait = 0;
                    }
                }
            } else {
                fileWait = 0;
            }
        }

        translate(viewWidth * viewAreaX / 100.0f, viewHeight * viewAreaY / 100.0f);
    }

    void saveImage() {

        File path = new File(filePath);
        if (path.mkdir()) {
            println("Directory " + filePath + " created");
        }
        println("Saving file \'" + filePath + "/" + fileBaseName + fileNumber + "." + fileExtension);
        File imageFile;
        String filename;
        do {
            fileNumber++;
            filename = filePath + "/" + fileBaseName + fileNumber + "." + fileExtension;
            imageFile = new File(filename);
        } while (imageFile.exists());
        save(filename);
    }
}
