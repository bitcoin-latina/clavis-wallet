package utils;

import java.math.BigInteger;

public class Converter {
    static BigInteger conversion = BigInteger.valueOf(1000000000000000000l);
    public static String Wei_To_Eth(BigInteger wei){
        BigInteger eth = wei.divide(conversion);
        return eth.toString();
    }
}
