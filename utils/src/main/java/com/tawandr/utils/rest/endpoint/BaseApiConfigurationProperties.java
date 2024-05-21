package com.tawandr.utils.rest.endpoint;

public class BaseApiConfigurationProperties {

	protected String sourceName;
	protected String sourceType;
	protected String language;
	protected String userName;
	protected String secretKey;


	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Override
	public String toString() {
		return "\n\tAllocationsApiProperties{" +
				"\n\t\tsourceName='" + sourceName + '\'' +
				",\n\t\tsourceType='" + sourceType + '\'' +
				",\n\t\tlanguage='" + language + '\'' +
				",\n\t\tuserName='" + userName + '\'' +
				"\n\t}";
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
