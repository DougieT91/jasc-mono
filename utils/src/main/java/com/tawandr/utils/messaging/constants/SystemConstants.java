package com.tawandr.utils.messaging.constants;

public class SystemConstants {
    public static final String LOCALE_LANGUAGE = "Language";
    public static final String DEFAULT_LOCALE = "en";
    public static final String DEFAULT_USER = "SimpleRestServiceDefaultUser";

    public static final String API_CLIENT_USERNAME = "Username";
    public static final String API_CLIENT_USERNAME_NARRATIVE = "API Client Username";

    public static final String SOURCE_TYPE = "Source-Type";
    public static final String SOURCE_NAME = "Source-Name";

    public static final String SOURCE_TYPE_NARRATIVE = " Supported clients [WEB,MOBILE-APP,SMS,USSD]";
    public static final String SOURCE_NAME_NARRATIVE = " Caller name, examples: [Ecorec Portal, ADS, eStore]";
    public static final String LOCALE_LANGUAGE_NARRATIVE = " Internationalisation language, supported [en]";

    public static final String CREDIT_SCHEME_BASIC_AUTH_REALM = "credit-scheme-basic-auth";
    public static final String BASIC_AUTH_REALM = "credit-scheme-basic-auth";

    public static final String ASPECT_LOG_MESSAGE_REQUEST_RECEIVED = "Received {} Request :: \n\tRequest : {}";
    public static final String ENCODING_KEY = "EncodingKey";
    public static final String STATUS_ALL = "All";
    public static final String STATUS_ACTIVE = "ACTIVE";

    public static final String NULL_OBJECT_FRIENDLY_ERROR_MESSAGE = "Operation [%s] Requires a non-null \"%s\"";
    public static final String TEMPLATE_DIR = "/data/resumex/templates";


    private SystemConstants() {
        super();
    }
}