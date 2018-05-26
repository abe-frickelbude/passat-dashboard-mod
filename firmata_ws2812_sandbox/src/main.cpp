
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

#include <Arduino.h>
#include <SoftwareSerial.h>
#include <Firmata.h>

#include "fab_led.h"

// custom SysEx command for Firmata
const byte PIXEL_COMMAND = 0x40;

// --------------------- LED state ---------------------------

const uint8_t NUM_PIXELS = 3;

// Min / max LED brightness (max possible 255)
const uint8_t MIN_BRIGHTNESS = 0;
const uint8_t MAX_BRIGHTNESS = 20;

// pixel state array
rgbw pixels[NUM_PIXELS] = {};

volatile byte currentBrightness = 1;
volatile bool stateChanged = false;

volatile bool testLedOn = false;

// SK6812 strip on PORT D, pin 6
sk6812b<D, 6> strip;

// ------------------------------------------------------------

void resetPixels();
void updatePixel(byte index, byte r, byte g, byte b, byte w);
void updateColors(char r, char g, char b, char w);
void sysexCallback(byte command, byte argc, byte *argv);

void setup()
{
    Firmata.setFirmwareNameAndVersion("WS2812 Sandbox", FIRMATA_FIRMWARE_MAJOR_VERSION, FIRMATA_FIRMWARE_MINOR_VERSION);
    Firmata.attach(START_SYSEX, sysexCallback);
    Firmata.begin(57600); // ?! Can't use a previously defined uint16_t constant here!

    pinMode(LED_BUILTIN, OUTPUT);

    // Turn off the LEDs
    resetPixels();

    //strip.clear(2 * NUM_PIXELS); <<---- Causes shifting of pixels by the number of positions passed to this method!! WTF ?!
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
        testLedOn = !testLedOn;
        digitalWrite(LED_BUILTIN, testLedOn ? HIGH : LOW);

        if (testLedOn)
        {
            updatePixel(0, 10, 0, 0, 1);
            updatePixel(1, 0, 10, 0, 1);
            updatePixel(2, 0, 0, 10, 1);
        }
        else
        {
            resetPixels();
        }
        strip.sendPixels(NUM_PIXELS, pixels);

        break;

    default:
        break;
    }
}

void updateColors(char r, char g, char b, char w)
{
    for (int i = 0; i < NUM_PIXELS; i++)
    {
        pixels[i].r = r;
        pixels[i].g = g;
        pixels[i].b = b;
        pixels[i].w = w;
    }
    stateChanged = true;
}

void updatePixel(byte index, byte r, byte g, byte b, byte w)
{
    pixels[index].r = r;
    pixels[index].g = g;
    pixels[index].b = b;
    pixels[index].w = w;
    stateChanged = true;
}

void resetPixels()
{
    for (int i = 0; i < NUM_PIXELS; i++)
    {
        pixels[i].r = pixels[i].g = pixels[i].b = pixels[i].w = 0x00;
    }
    stateChanged = true;
}