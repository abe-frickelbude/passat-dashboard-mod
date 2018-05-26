
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

void sysexCallback(byte command, byte argc, byte *argv);

void setup()
{
    Firmata.setFirmwareNameAndVersion("WS2812 Sandbox", FIRMATA_FIRMWARE_MAJOR_VERSION, FIRMATA_FIRMWARE_MINOR_VERSION);
    Firmata.attach(START_SYSEX, sysexCallback);
    Firmata.begin(57600); // ?! Can't use a previously defined uint16_t constant here!

    pinMode(LED_BUILTIN, OUTPUT);
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
        break;

    default:
        break;
    }
}