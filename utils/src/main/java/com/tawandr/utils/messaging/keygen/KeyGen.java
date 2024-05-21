package com.tawandr.utils.messaging.keygen;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class KeyGen {

	public static String generateKey() {
		StringBuilder id = new StringBuilder();
		for (int x = 0; x < 4; x++) {
			id.append((char) (int) (Math.random() * 25 + 65));
		}
		id.append(System.currentTimeMillis());
		return id.toString();
	}

	public static void main(String... strings) {
		System.out.println(getUniqueId());

		log.info(">> Generated Number Array : {}", generateDoubleArray(10, -100, 100, 3));
		log.info(">> Generated Long : {}", generateLong());
		log.info(">> Generated Double : {}", generateDouble(2));
		log.info(">> Generated Absolute Int : {}", generateInt(true));
		log.info("Random number{}", Math.random());
	}

	public static Long getUniqueId() {
		return GenerateKey.generateEntityId();
	}


	/**************************************** <Int> ****************************************/

	public static int generateInt() {
		return generateInt(false);
	}

	public static int generateInt(boolean absolute) {
		if(absolute) return Math.abs(ThreadLocalRandom.current().nextInt());
		return ThreadLocalRandom.current().nextInt();
	}

	public static int generateInt(boolean absolute, int lower, int upper) {
	    if(absolute) return Math.abs(ThreadLocalRandom.current().nextInt(lower, upper + 1));
		return ThreadLocalRandom.current().nextInt(lower, upper + 1);
	}

	public static int generateInt(int lower, int upper) {
		return ThreadLocalRandom.current().nextInt(lower, upper + 1);
	}

    public static int[] generateIntArray(int arraySize, int lower, int upper) {
        int[] ints = new int[arraySize];
        for (int counter = 0; counter < arraySize; counter++) {
            ints[counter] = generateInt(false, lower, upper);
        }
        return ints;
    }

    public static List<Integer> generateIntegerList(int arraySize, int lower, int upper) {
	    int[] ints = generateIntArray( arraySize,  lower,  upper);
        List<Integer> integerList = new ArrayList<>();
        for(int count = 0; count < arraySize; count++){
            integerList.add(ints[count]);
        }
        return integerList;
    }

    /**************************************** </Int> ****************************************/

	/**************************************** <long> ****************************************/

	public static long generateLong() {
		return generateLong(false);
	}

	public static long generateLong(boolean absolute) {
		if(absolute) return Math.abs(ThreadLocalRandom.current().nextLong());
		return ThreadLocalRandom.current().nextLong();
	}

	public static long generateLong(boolean absolute, int lower, int upper) {
	    if(absolute) return Math.abs(ThreadLocalRandom.current().nextLong(lower, upper + 1));
		return ThreadLocalRandom.current().nextLong(lower, upper + 1);
	}

	public static long generateLong(int lower, int upper) {
		return ThreadLocalRandom.current().nextLong(lower, upper + 1);
	}

    public static long[] generateLongArray(int arraySize, int lower, int upper) {
        long[] longs = new long[arraySize];
        for (int counter = 0; counter < arraySize; counter++) {
            longs[counter] = generateLong(false, lower, upper);
        }
        return longs;
    }

    public static List<Long> generateLongList(int arraySize, int lower, int upper) {
	    long[] longs = generateLongArray(arraySize,  lower,  upper);
        List<Long> integerList = new ArrayList<>();
        for(int count = 0; count < arraySize; count++){
            integerList.add(longs[count]);
        }
        return integerList;
    }

    /**************************************** </long> ****************************************/

	/**************************************** <Double> ****************************************/

	public static double generateDouble() {
		return generateDouble(false);
	}

	public static double generateDouble(int precision) {
		return generateDouble(false, precision);
	}

	public static double setDoublePrecision(Double value, int precision) {
		value = BigDecimal.valueOf(value).setScale(precision, RoundingMode.HALF_UP).doubleValue();
		return value;
	}

	public static double generateDouble(boolean absolute) {
		if(absolute) return Math.abs(ThreadLocalRandom.current().nextDouble());
		return ThreadLocalRandom.current().nextDouble();
	}

	public static double generateDouble(boolean absolute, int precision) {
	    double nextDouble = ThreadLocalRandom.current().nextDouble();
        nextDouble = BigDecimal.valueOf(nextDouble).setScale(precision, RoundingMode.HALF_UP).doubleValue();
        return nextDouble;
	}

	public static double generateDouble(boolean absolute, int lower, int upper) {
	    if(absolute) return Math.abs(ThreadLocalRandom.current().nextDouble(lower, upper + 1));
		return ThreadLocalRandom.current().nextDouble(lower, upper + 1);
	}

	public static double generateDouble(boolean absolute, int lower, int upper, int precision) {
		return setDoublePrecision(generateDouble(absolute,lower,upper), precision);
	}

	public static double generateDouble(int lower, int upper) {
		return ThreadLocalRandom.current().nextDouble(lower, upper + 1);
	}

    public static double[] generateDoubleArray(int arraySize, int lower, int upper) {
        double[] doubles = new double[arraySize];
        for (int counter = 0; counter < arraySize; counter++) {
            doubles[counter] = generateDouble(false, lower, upper);
        }
        return doubles;
    }

    public static double[] generateDoubleArray(int arraySize, int lower, int upper, int precision) {
        double[] doubles = new double[arraySize];
        for (int counter = 0; counter < arraySize; counter++) {
            doubles[counter] = generateDouble(false, lower, upper);
            doubles[counter] = BigDecimal.valueOf(doubles[counter]).setScale(precision, RoundingMode.HALF_UP).doubleValue();
        }
        return doubles;
    }

    public static List<Double> generateDoubleList(int arraySize, int lower, int upper) {
	    double[] doubles = generateDoubleArray( arraySize,  lower,  upper);
        List<Double> doubleList = new ArrayList<>();
        for(int count = 0; count < arraySize; count++){
            doubleList.add(doubles[count]);
        }
        return doubleList;
    }

    /**************************************** </Double> ****************************************/
}
