package com.tawandr.utils.messaging.dtos;

import java.security.Principal;

public class ApiRequestMetaData {
    private String sourceName;
    private String sourceType;
    private String language;
    private Principal principal;
    private String userName;

    private ApiRequestMetaData(String sourceName,
                               String sourceType,
                               String language,
                               Principal principal) {
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.language = language;
        this.principal = principal;
    }

    public static Builder builder() {
        return new Builder();
    }

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

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public static class Builder {
        private String sourceName;
        private String sourceType;
        private String language;
        private Principal principal;

        public ApiRequestMetaData build() {
            return new ApiRequestMetaData(sourceName, sourceType, language, principal);
        }

        public Builder sourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public Builder sourceType(String sourceType) {
            this.sourceType = sourceType;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder principal(Principal principal) {
            this.principal = principal;
            return this;
        }
    }

    @Override
    public String toString() {
        return "\n\tApiRequestMetaData{" +
                "\n\t\tsourceName='" + sourceName + '\'' +
                ",\n\t\tsourceType='" + sourceType + '\'' +
                ",\n\t\tlanguage='" + language + '\'' +
                ",\n\t\tprincipal=" + principal +
                ",\n\t\tuserName='" + userName +
                "\n\t}";
    }
}
