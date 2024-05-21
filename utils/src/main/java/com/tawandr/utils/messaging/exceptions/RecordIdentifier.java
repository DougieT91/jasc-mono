package com.tawandr.utils.messaging.exceptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class RecordIdentifier implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Map<Object, Object> identifierComponents;

	private RecordIdentifier() {
		identifierComponents = new HashMap<>();
	}

	public Map<Object, Object> getIdentifierComponents() {
		return Collections.unmodifiableMap(identifierComponents);
	}

	public static Builder builder(){
		return new Builder();
	}

	public static class Builder {

		private final RecordIdentifier identifier;

		public Builder() {
			identifier = new RecordIdentifier();
		}

		public Builder withKey(Object key, Object value) {
			identifier.identifierComponents.put(key, value);
			return this;
		}

		public Builder removeKey(Object key) {
			identifier.identifierComponents.remove(key);
			return this;
		}

		public RecordIdentifier build() {
			return identifier;
		}

	}

	@Override
	public String toString() {
		return "\n\tRecordIdentifier{" +
				"\n\t\tidentifierComponents=" + identifierComponents +
				"\n\t}";
	}
}
