package com.CyclicRedundancyCheck;

import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException {
        String message = args[1];
        CRC coder;
        if(args.length == 2) {
            coder = new CRC(message);
        } else if (args.length == 3) {
            coder = new CRC(message, args[2]);
        } else {
            throw new IOException("Incorrect arguments!");
        }
        if (args[0].equals("-c")) {
            String encodedText = coder.encode();
            System.out.println("Encoded text: " + encodedText);
        } else if (args[0].equals("-d")) {
            String decodeText = coder.decode();
            System.out.println("Decoded text: " + decodeText);
        }
    }
}

