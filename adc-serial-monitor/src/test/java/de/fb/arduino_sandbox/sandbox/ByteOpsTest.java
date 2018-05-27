package de.fb.arduino_sandbox.sandbox;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteOpsTest {

    private static final Logger log = LoggerFactory.getLogger(ByteOpsTest.class);

    @Test
    public void testByteRange() {

        byte byte1 = (byte) 200;
        log.info("Byte1: {}", byte1);

    }

}
