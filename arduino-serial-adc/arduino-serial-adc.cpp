#include <Arduino.h>
#include <HardwareSerial.h>

const int INPUT_PIN = A0;

//const int BAUD_RATE = 9600;
//const int BAUD_RATE = 14400;
const int BAUD_RATE = 19200;
//const int BAUD_RATE = 28800;
//const int BAUD_RATE = 38400;
//const int BAUD_RATE = 57600;
//const int BAUD_RATE = 115200;

const int MAX_DIGITAL_PIN = 14;

void setup() {

	pinMode(INPUT_PIN, INPUT);
	Serial.begin(BAUD_RATE);
	while (!Serial) {
		// wait until connected
	}

	// Turn off everything (not on RXTX)
	for (int index = 2; index < MAX_DIGITAL_PIN; index++) {
		pinMode(index, OUTPUT);
		digitalWrite(index, LOW);
	}
}

void loop() {

	/* we don't send over any more data if the port is blocked.
	 *
	 * This effectively means that if data cannot be read quickly enough "on the other side" without blocking,
	 * some ADC samples will be dropped!
	 */
	//if (Serial.availableForWrite() >= sizeof(int)) {

	int value = analogRead(INPUT_PIN);
	Serial.println(value);

	//Serial.println(123456789);
	//Serial.flush();
	//}
	//int value = analogRead(INPUT_PIN);
	//Serial.println(value);
	//delay(10);
}
