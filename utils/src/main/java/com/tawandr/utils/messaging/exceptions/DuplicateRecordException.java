package com.tawandr.utils.messaging.exceptions;

public class DuplicateRecordException extends BusinessException {

	private static final long serialVersionUID = 1L;
	private final String recordName;
	private final RecordIdentifier identifier;

	public DuplicateRecordException(String recordName, RecordIdentifier identifier, String message) {
		super(message);
		this.recordName = recordName;
		this.identifier = identifier;
	}

	public String getRecordName() {
		return recordName;
	}

	public RecordIdentifier getIdentifier() {
		return identifier;
	}

}
