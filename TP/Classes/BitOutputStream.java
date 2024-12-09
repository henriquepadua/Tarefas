import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {
    private OutputStream out;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream out) {
        this.out = out;
        this.currentByte = 0;
        this.numBitsFilled = 0;
    }

    public void write(int bits, int numBits) throws IOException {
        while (numBits > 0) {
            int bitsToWrite = Math.min(8 - numBitsFilled, numBits);
            currentByte |= (bits & ((1 << bitsToWrite) - 1)) << numBitsFilled;
            numBitsFilled += bitsToWrite;
            bits >>>= bitsToWrite;
            numBits -= bitsToWrite;

            if (numBitsFilled == 8) {
                out.write(currentByte);
                currentByte = 0;
                numBitsFilled = 0;
            }
        }
    }

    public void close() throws IOException {
        if (numBitsFilled > 0) {
            out.write(currentByte);
        }
        out.close();
    }
}