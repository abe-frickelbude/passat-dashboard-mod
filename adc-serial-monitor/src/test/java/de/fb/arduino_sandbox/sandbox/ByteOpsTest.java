package de.fb.arduino_sandbox.sandbox;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.fb.arduino_sandbox.service.firmata.ByteUtils;
import de.fb.arduino_sandbox.util.bit_sequence.BitSequence;
import de.fb.arduino_sandbox.util.bit_sequence.BitSequence.ALIGN;
import de.fb.arduino_sandbox.util.bit_sequence.BitSequence.GROUP;

public class ByteOpsTest {

    private static final Logger log = LoggerFactory.getLogger(ByteOpsTest.class);

    // @Test
    public void testByteRange() {

        byte byte1 = (byte) 200;
        log.info("Byte1: {}", byte1);
    }

    // @Test
    public void test8bitBytesToShort() {

        short source = 987;
        byte part1 = (byte) (source & 0x00FF);
        byte part2 = (byte) ((source) >>> 8 & 0x00FF);

        short target = (short) (((part2 & 0xFF) << 8) | (part1 & 0xFF));

        log.info("Source: {}", Integer.toBinaryString(source));
        log.info("Part1:  {}", Integer.toBinaryString(part1));
        log.info("Part2:  {}", Integer.toBinaryString(part2));

        log.info("\n\n");
        log.info("source: {}, target: {}", source, target);
    }

    // This works correctly!
    // @Test
    public void test7bitBytesToShort() {

        short source = 756;
        BitSequence sourceBits = new BitSequence(source, 16);

        log.info("Source: {}, value: {}", sourceBits.toBynaryString(ALIGN.LEFT, GROUP.BYTE), source);

        BitSequence lower = sourceBits.getSubSequence(9, 7);
        log.info("Lower:  {}", lower.toBynaryString(ALIGN.LEFT, GROUP.BYTE));

        BitSequence higher = sourceBits.getSubSequence(2, 7);
        log.info("Higher: {}", higher.toBynaryString(ALIGN.LEFT, GROUP.BYTE));

        BitSequence target = new BitSequence((short) 0x0000, 16);
        target = target.or(lower, ALIGN.RIGHT);

        higher.shiftLeft(7);
        target = target.or(higher);

        log.info("Target: {}, value: {}", target.toBynaryString(ALIGN.LEFT, GROUP.BYTE), target.getAsBigInteger());
    }

    @Test
    public void test7bitBytesToShortNative() {

        short source = 874;

        // -------------------- Split bytes -------------------------------

        byte lower = (byte) (source & 0x007F);
        byte higher = (byte) ((source >> 7) & 0x007F);

        ////////////////////////

        // written out as individual steps for reference!
        // short result = 0x0000;
        // result |= (byte)(lower & 0x7F);
        // result |= (higher << 7);

        short result = (short) ((lower & 0x7F) | (short) (higher << 7));

        // ---------------------- check bytes ------------------------------

        BitSequence sourceBits = new BitSequence(source, 16);
        BitSequence lowerByte = new BitSequence(lower, 8);
        BitSequence higherByte = new BitSequence(higher, 8);

        log.info("Source:   {}, value: {}", sourceBits.toBynaryString(ALIGN.LEFT, GROUP.BYTE), source);
        log.info("Lower:    {}", lowerByte.toBynaryString(ALIGN.LEFT, GROUP.BYTE));
        log.info("Higher:   {}", higherByte.toBynaryString(ALIGN.LEFT, GROUP.BYTE));

        // ----------------------- check result ------------------------------

        BitSequence targetBits = new BitSequence(result, 16);
        log.info("Target:   {}, value: {}", targetBits.toBynaryString(ALIGN.LEFT, GROUP.BYTE), targetBits.getAsBigInteger());
        log.info("Result:   {}", result);
    }
}
