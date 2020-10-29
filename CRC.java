package com.CyclicRedundancyCheck;



public class CRC {
    private final String message;
    private final String generatorPol;

    public CRC(String message) {
        //default CRC-8
        this(message, "111010101");
    }

    public CRC(String message, String generatorPol) {
        this.message = message;
        this.generatorPol = generatorPol;
    }

    private String XOR(String dividend, String divisor) {
        StringBuilder remainder = new StringBuilder();
        char []divArr = dividend.toCharArray();
        char []dsArr = divisor.toCharArray();
        for(int i = 0; i < dividend.length(); i++) {
            if (divArr[i] == dsArr[i]) {
                remainder.append('0');
            } else {
                remainder.append('1');
            }
        }
        return remainder.toString();
    }

    public String encode() {
        StringBuilder encodedMessage = new StringBuilder(message);
        encodedMessage.append("0".repeat(Math.max(0, generatorPol.length() - 1)));

        String dividend = encodedMessage.toString();
        String divisor = generatorPol;

        StringBuilder tempDividend = new StringBuilder();
        StringBuilder tempDivisor = new StringBuilder(divisor);

        int count = 0;
        for (int i = 0; i < dividend.length(); i++) {
            if (dividend.charAt(i) == '0') {
                count++;
            } else {
                break;
            }
        }
        dividend = dividend.substring(count);

        while(dividend.length() >= divisor.length()) {
            tempDivisor.append("0".repeat(Math.max(0, dividend.length() - divisor.length())));
            tempDividend.append(XOR(dividend, tempDivisor.toString()));

            for (int i = 0; i < dividend.length(); i++) {
                if (tempDividend.charAt(i) == '0') {
                    dividend = tempDividend.substring(i + 1);
                } else {
                    break;
                }
            }

            tempDividend = new StringBuilder();
            tempDivisor = new StringBuilder(divisor);
        }
        StringBuilder result = new StringBuilder(dividend);
        while (!(result.length() == encodedMessage.length() - message.length())) {
            result.insert(0, "0");
        }
        return message + result.toString();
    }

    public String decode() {
        String dividend = message;
        String divisor = generatorPol;

        StringBuilder tempDividend = new StringBuilder();
        StringBuilder tempDivisor = new StringBuilder(divisor);

        int count = 0;
        for (int i = 0; i < dividend.length(); i++) {
            if (dividend.charAt(i) == '0') {
                count++;
            } else {
                break;
            }
        }
        dividend = dividend.substring(count);
        String remainder = null;
        
        while(dividend.length() >= divisor.length()) {
            tempDivisor.append("0".repeat(Math.max(0, dividend.length() - divisor.length())));
            tempDividend.append(XOR(dividend, tempDivisor.toString()));

            remainder = tempDividend.toString();
            for (int i = 0; i < dividend.length(); i++) {
                if (tempDividend.charAt(i) == '0') {
                    dividend = tempDividend.substring(i + 1);
                } else {
                    break;
                }
            }

            tempDividend = new StringBuilder();
            tempDivisor = new StringBuilder(divisor);
        }

        assert remainder != null;
        if (remainder.length() > generatorPol.length()) {
            remainder = remainder.substring(remainder.length() - generatorPol.length());
        }

        if (dividend.contains("1")) {
            System.out.println("Error detected!");
            System.out.println("Remainder: " + remainder);
        } else {
            System.out.println("Errors not detected.");
            System.out.println("Remainder: " + remainder);
        }

        return message.substring(0, message.length() - (generatorPol.length() - 1));
    }
}

