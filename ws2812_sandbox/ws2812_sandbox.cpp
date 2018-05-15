////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
/// Fast Adressable Bitbang LED Library
/// Copyright (c)2015, 2017 Dan Truong
///
/// This is the simplest exmple to use the library.
///
/// This example is for an Arduino Uno board with a LED strip connected to
/// port D6. Targetting any other board requires you to change something.
/// The program sends an array of pixels to display on the strip.
/// "strip" represents the hardware: LED types and port configuration,
/// "pixels" represents the data sent to the LEDs: a series of colors.
///
/// Wiring:
///
/// The LED strip DI (data input) line should be on port D6 (Digital pin 6 on
/// Arduino Uno). If you need to change the port, change all declarations below
/// from, for example from "ws2812b<D,6> myWs2812" to "ws2812b<B,4> myWs2812"
/// if you wanted to use port B4.
/// The LED power (GND) and (+5V) should be connected on the Arduino Uno's GND
/// and +5V.
///
/// Visual results:
///
/// If the hardware you use matches this program you will see the LEDs blink
/// repeatedly red, green, blue, white, in that order.
///
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

#include <Arduino.h>
#include "FAB_LED.h"

enum LedState {
	INIT,
	RAMP_UP,
	RAMP_DOWN,
	SWITCH_OVER
};

// Min / max LED brightness (max possible 255)
const uint8_t MIN_BRIGHTNESS = 0;
const uint8_t MAX_BRIGHTNESS = 16;

// How many pixels to control
const uint8_t NUM_PIXELS = 20;

// Declare the LED protocol and the port
sk6812<D, 6> strip;

uint8_t currentBrightness = 1;

// The pixel array to display
rgbw pixels[NUM_PIXELS] = { };

LedState ledState = INIT;

////////////////////////////////////////////////////////////////////////////////
// Sets the array to specified color
////////////////////////////////////////////////////////////////////////////////
void updateColors(char r, char g, char b, char w) {
	for (int i = 0; i < NUM_PIXELS; i++) {
		pixels[i].r = r;
		pixels[i].g = g;
		pixels[i].b = b;
		pixels[i].w = w;
	}
}

void setup() {
	// Turn off the LEDs
	strip.clear(2 * NUM_PIXELS);
}

void loop() {

//  // Write the pixel array red
//  updateColors(currentBrightness, 0 , 0, 0);
//  // Display the pixels on the LED strip
//  strip.sendPixels(NUM_PIXELS, pixels);
//  // Wait 0.1 seconds
//  delay(1000);
//
//  // Write the pixel array green
//  updateColors(0, currentBrightness, 0, 0);
//  // Display the pixels on the LED strip
//  strip.sendPixels(NUM_PIXELS, pixels);
//  // Wait 0.1 seconds
//  delay(1000);
//
//  // Write the pixel array blue
//  updateColors(0, 0, currentBrightness, 0);
//  // Display the pixels on the LED strip
//  strip.sendPixels(NUM_PIXELS, pixels);
//  // Wait 0.1 seconds
//  delay(1000);
//
//  // Write the pixel array white
//  updateColors( currentBrightness, currentBrightness, currentBrightness, 0);
//  // Display the pixels on the LED strip
//  strip.sendPixels(NUM_PIXELS, pixels);
//  // Wait 0.1 seconds
//  delay(1000);

	// Write the pixel array warm white
//	updateColors(0, 0, 0, currentBrightness);
//	strip.sendPixels(NUM_PIXELS, pixels);
//
//	if(currentBrightness < MAX_BRIGHTNESS) {
//		currentBrightness++;
//	}
//
//	currentBrightness++;
//	if(currentBrightness > MAX_BRIGHTNESS) {
//		currentBrightness = 1;
//	}

	switch (ledState) {

	case INIT:

		// blank LEDs
		strip.clear(2 * NUM_PIXELS);
		ledState = RAMP_UP;
		break;

	case RAMP_UP:

		// Actual order is G,R,B,W!
		updateColors(2, 10, 6, currentBrightness);
		strip.sendPixels(NUM_PIXELS, pixels);

		if (currentBrightness < MAX_BRIGHTNESS) {
			currentBrightness++;
		} else {
			ledState = RAMP_DOWN;
		}
		break;

	case RAMP_DOWN:

		updateColors(3, 11, 1, currentBrightness);
		strip.sendPixels(NUM_PIXELS, pixels);

		if (currentBrightness > MIN_BRIGHTNESS) {
			currentBrightness--;
		} else {
			ledState = INIT;
		}
		break;
	}

	delay(5000);
}
