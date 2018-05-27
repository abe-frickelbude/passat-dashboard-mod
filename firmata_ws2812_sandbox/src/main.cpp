
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

#include <Arduino.h>
#include <Firmata.h>
#include <SoftwareSerial.h>

#include "fab_led.h"

// custom SysEx commands for pixel operations
const byte PIXEL_COMMAND = 0x40;

const byte SET_PIXEL = 0x10;
const byte RESET_PIXEL = 0x11;
const byte RESET_ALL = 0x12;

// --------------------- LED state ---------------------------

const uint8_t NUM_PIXELS = 32;

// pixel state array
rgbw pixels[NUM_PIXELS] = {};

volatile bool stateChanged = false;
// volatile bool testLedOn = false;

// SK6812 strip on PORT D, pin 6
sk6812b<D, 6> strip;

// ------------------------------------------------------------

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
    Firmata.begin(57600); // ?! Can't use a previously defined uint16_t constant here!

    pinMode(LED_BUILTIN, OUTPUT);

    // Turn off the LEDs
    //resetPixels();

    // strip.clear(2 * NUM_PIXELS); <<---- Causes shifting of pixels by the number
    // of positions passed to this method!! WTF ?!
    strip.clear(NUM_PIXELS);
}

void loop()
{
    while (Firmata.available())
    {
        Firmata.processInput();
    }
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

    case SET_PIXEL:
    {
        uint8_t index = argv[0] & 0x7F;
        uint8_t r = (argv[1] & 0x7F) | ((argv[2] & 0x7F) << 7);
        uint8_t g = (argv[3] & 0x7F) | ((argv[4] & 0x7F) << 7);
        uint8_t b = (argv[5] & 0x7F) | ((argv[6] & 0x7F) << 7);
        uint8_t w = (argv[7] & 0x7F) | ((argv[8] & 0x7F) << 7);
        setPixel(index, r, g, b, w);
        strip.sendPixels(NUM_PIXELS, pixels);
    }
    break;

    case RESET_PIXEL:
    {
        uint8_t index = argv[0] & 0x7F;
        setPixel(index, 0x00, 0x00, 0x00, 0x00);
        strip.sendPixels(NUM_PIXELS, pixels);
    }
    break;

    case RESET_ALL:
        strip.clear(NUM_PIXELS);
        break;
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

// testLedOn = !testLedOn;
// digitalWrite(LED_BUILTIN, testLedOn ? HIGH : LOW);

// if (testLedOn)
// {
//     updatePixel(0, 10, 0, 0, 1);
//     updatePixel(1, 0, 10, 0, 1);
//     updatePixel(2, 0, 0, 10, 1);
// }
// else
// {
//     resetPixels();
// }
// strip.sendPixels(NUM_PIXELS, pixels);