#include <Arduino.h>

const int INPUT_PIN = A0;

//const int BAUD_RATE = 9600;
//const int BAUD_RATE = 14400;
const int BAUD_RATE = 19200;
//const int BAUD_RATE = 28800;
//const int BAUD_RATE = 38400;
//const int BAUD_RATE = 57600;
//const int BAUD_RATE = 115200;

void setup() {

	pinMode(INPUT_PIN, INPUT);
	Serial.begin(BAUD_RATE);
	while(!Serial) {
		// wait until connected
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
		//Serial.flush();
	//}

	//int value = analogRead(INPUT_PIN);
	//Serial.println(value);
	//delay(10);
}
