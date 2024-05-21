package com.tawandr.utils.messaging.exceptions;

/**
 * Created By Dougie T Muringani : 3/5/2020
 *
*/

public class InvalidStateException extends BusinessException {

	private static final long serialVersionUID = 1L;
	private final RecordIdentifier recordIdentifier;

	public InvalidStateException(RecordIdentifier recordIdentifier, String message, String friendlyMessage) {
		super(message, friendlyMessage);
		this.recordIdentifier = recordIdentifier;
	}

	public InvalidStateException(String message, String friendlyMessage) {
		super(message, friendlyMessage);
		this.recordIdentifier = RecordIdentifier.builder().build();
	}

	public RecordIdentifier getRecordIdentifier() {
		return recordIdentifier;
	}


}
