package com.tawandr.utils.messaging.keygen;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenerateKey {

    public static long generateEntityId() {

        long currentTimeMillis = System.currentTimeMillis();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            builder.append((int) (Math.random() * 10));
        }
        int randomNumber = (int) (Math.random() * 1000);

        final String entityId = currentTimeMillis + builder.toString() + randomNumber;
        final long abs = Math.abs(currentTimeMillis);
        try {
            currentTimeMillis = Long.parseLong(entityId);
        } catch (Exception e) {
            return abs;
        }
        log.info("Generated new Entity Id: {}", abs);
        return abs;
    }

    public static void main(String... strings) {
        log.info(">> Generated Key : {}", + generateEntityId());
    }


}
