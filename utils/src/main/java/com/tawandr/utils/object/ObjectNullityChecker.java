package com.tawandr.utils.object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created By Dougie T Muringani : 24/12/2018
 */
public class ObjectNullityChecker {

    private static final Logger log = LoggerFactory.getLogger(ObjectNullityChecker.class);

    public static boolean objectIsNull(Object object) {
        return object == null;
    }

    public static Byte objectEquivalentValue(byte number) {
        Byte response;
        response = number == 0 ? null : number;
        log.info("converted primitive value [{}] to Object [{}]", number, response);
        return response;
    }

    public static Short objectEquivalentValue(short number) {
        Short response;
        response = number == 0 ? null : number;
        log.info("converted primitive value [{}] to Object [{}]", number, response);
        return response;
    }

    public static Integer objectEquivalentValue(int number) {
        Integer response;
        response = number == 0 ? null : number;
        log.info("converted primitive value [{}] to Object [{}]", number, response);
        return response;
    }

    public static Long objectEquivalentValue(long number) {
        Long response;
        response = number == 0 ? null : number;
        log.info("converted primitive value [{}] to Object [{}]", number, response);
        return response;
    }

    public static Float objectEquivalentValue(float number) {
        return number == 0 ? null : number;
    }

    public static Double objectEquivalentValue(double number) {
        return number == 0 ? null : number;
    }

    public static byte objectEquivalentValue(Byte number) {
        return number == null ? 0 : number;
    }

    public static short objectEquivalentValue(Short number) {
        return number == null ? 0 : number;
    }

    public static int objectEquivalentValue(Integer number) {
        return number == null ? 0 : number;
    }

    public static long objectEquivalentValue(Long number) {
        return number == null ? 0 : number;
    }

    public static float objectEquivalentValue(Float number) {
        return number == null ? 0 : number;
    }

    public static double objectEquivalentValue(Double number) {
        return number == null ? 0 : number;
    }

    public static void main(String[] args) {
        Long lw = null;
        Byte bw = null;
        Short sw = null;
        Integer iw = null;
        Float fw = null;
        Double dw = null;

        long lp = 0;
        byte bp = 0;
        short sp = 0;
        int ip = 0;
        float fp = 0;
        double dp = 0;

        Long result = objectEquivalentValue(lw);
        log.info("Resulting value:: {}", result);
    }

    private ObjectNullityChecker() {
        super();
    }
}