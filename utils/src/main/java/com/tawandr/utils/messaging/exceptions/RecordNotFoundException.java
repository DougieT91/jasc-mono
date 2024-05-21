package com.tawandr.utils.messaging.exceptions;

import java.util.AbstractMap;
import java.util.Map;

public class RecordNotFoundException extends BusinessException {
    // Todo: [28/5/2020]{@tmuringani} : Implement Method make this exception somewhat generic

    private static final long serialVersionUID = 1L;
    private final String recordName;
    private final RecordIdentifier identifier;

    public RecordNotFoundException(String recordName,
                                   RecordIdentifier identifier,
                                   String message) {
        super(message);
        this.recordName = recordName;
        this.identifier = identifier;
    }

    public RecordNotFoundException(Object id,
                                   String message) {
        super(message);
        this.recordName = id.toString();
        this.identifier = RecordIdentifier.builder().withKey("id", id).build();
        ;
    }

    public String getRecordName() {
        return recordName;
    }

    public RecordIdentifier getIdentifier() {
        return identifier;
    }

    public Map.Entry<Object, Object> getFirstIdentifier() {
        return getIdentifier()
                        .getIdentifierComponents()
                        .entrySet()
                        .stream()
                        .findFirst()
                        .orElse(new AbstractMap.SimpleEntry<>("id", null));

    }
}
