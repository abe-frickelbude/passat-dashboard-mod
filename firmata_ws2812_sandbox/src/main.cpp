
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

#include <math.h>
#include <Arduino.h>
#include <Firmata.h>
#include <SoftwareSerial.h>

#include "fab_led.h"

// custom SysEx commands for pixel operations
const byte PIXEL_COMMAND = 0x40;

const byte SET_NUM_PIXELS = 0x01;
const byte SET_PIXEL = 0x02;
const byte RESET_PIXEL = 0x03;
const byte RESET_ALL = 0x04;
const byte SHOW_PIXELS = 0x05;

const byte ANALOG_READ_COMMAND = 0x41;

// analog input
const byte ANALOG_INPUT_PIN = PIN_A1;

const float ADC_SCALING_FACTOR = 255.0f / 1023.0f;

// --------------------- LED state ---------------------------

// maximum allowed number of pixels
const uint8_t NUM_PIXELS = 64;

// pixel state array
rgbw pixels[NUM_PIXELS] = {};

// current number of active pixels
volatile int numPixels = 0;

// used to watch for data modifications in the main loop
volatile bool stateChanged = false;

// SK6812 strip on PORT D, pin 2
sk6812b<D, 2> strip;

// ------------------------------------------------------------

void sendAnalog(byte pin, uint16_t value);
byte scaleAnalogValue(uint16_t value);

void resetPixels();
void setPixel(byte index, byte r, byte g, byte b, byte w);
void sysexCallback(byte command, byte argc, byte *argv);
void pixelCallback(byte command, byte argc, byte *argv);

void setup()
{
    Firmata.setFirmwareNameAndVersion("WS2812 Sandbox",
                                      FIRMATA_FIRMWARE_MAJOR_VERSION,
                                      FIRMATA_FIRMWARE_MINOR_VERSION);

    Firmata.attach(START_SYSEX, sysexCallback);
    Firmata.begin(115200); // ?! Can't use a previously defined uint16_t constant here!

    pinMode(LED_BUILTIN, OUTPUT);
    //pinMode(ANALOG_INPUT_PIN, INPUT);

    // initial reset
    strip.clear(NUM_PIXELS);
}

void loop()
{
    // process input commands
    while (Firmata.available())
    {
        Firmata.processInput();
    }

    // update and send analog input(s)
    //Firmata.sendAnalog(ANALOG_INPUT_PIN, scaleAnalogValue(analogRead(ANALOG_INPUT_PIN)));
    Firmata.sendAnalog(ANALOG_INPUT_PIN, analogRead(ANALOG_INPUT_PIN));
    //sendAnalog(ANALOG_INPUT_PIN, analogRead(ANALOG_INPUT_PIN));

    if (stateChanged)
    {
        //strip.clear(numPixels);
        strip.sendPixels(numPixels, pixels);
        stateChanged = false;
    }
}

byte scaleAnalogValue(uint16_t value)
{
    return static_cast<byte>(value * ADC_SCALING_FACTOR);
}

void sendAnalog(byte pin, uint16_t value)
{

    Firmata.startSysex();
    Firmata.write(ANALOG_READ_COMMAND);
    Firmata.write(pin);
    Firmata.sendValueAsTwo7bitBytes(value);
    Firmata.endSysex();
}

void sysexCallback(byte command, byte argc, byte *argv)
{
    switch (command)
    {
    case PIXEL_COMMAND:
        if (argc < 1)
            return;
        // pass all pixel commands to custom callback
        pixelCallback(argv[0], argc - 1, argv + 1);
        break;

    default:
        break;
    }
}

void pixelCallback(byte command, byte argc, byte *argv)
{
    switch (command)
    {

    case SET_NUM_PIXELS:
    {
        numPixels = argv[0] & 0x7F;
    }
    break;

    case SET_PIXEL:
    {
        uint8_t index = argv[0] & 0x7F;
        uint8_t r = (argv[1] & 0x7F) | ((argv[2] & 0x7F) << 7);
        uint8_t g = (argv[3] & 0x7F) | ((argv[4] & 0x7F) << 7);
        uint8_t b = (argv[5] & 0x7F) | ((argv[6] & 0x7F) << 7);
        uint8_t w = (argv[7] & 0x7F) | ((argv[8] & 0x7F) << 7);
        setPixel(index, r, g, b, w);
    }
    break;

    case RESET_PIXEL:
    {
        uint8_t index = argv[0] & 0x7F;
        setPixel(index, 0x00, 0x00, 0x00, 0x00);
    }
    break;

    case RESET_ALL:
    {
        //strip.clear(numPixels);
        strip.clear(NUM_PIXELS);
        break;
    }

    case SHOW_PIXELS:
    {
        stateChanged = true;
        break;
    }
    }
}

void setPixel(byte index, byte r, byte g, byte b, byte w)
{
    pixels[index].r = r;
    pixels[index].g = g;
    pixels[index].b = b;
    pixels[index].w = w;
}

void resetPixels()
{
    for (int i = 0; i < NUM_PIXELS; i++)
    {
        pixels[i].r = pixels[i].g = pixels[i].b = pixels[i].w = 0x00;
    }
}