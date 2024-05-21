package com.tawandr.utils.messaging.enums;

import static com.tawandr.utils.object.StringUtils.toSentence;

/**
 * Created By Dougie T Muringani : 4/6/2019
 */
public enum ApplicationResponseCode {
    //Custom Codes: Common 9**
    SUCCESS (200),
    INVALID_MOBILE (901),
    DUPLICATE_REQUEST (902),
    ORIGINAL_TRANSACTION_FAILED (906),
    ORIGINAL_TRANSACTION_NOT_FOUND (907),
    UNKNOWN_VALIDATION_ERROR (910),
    INVALID_INPUT_VALUE (911),
    TRANSACTION_FAILED(919),
    OPERATION_NOT_ALLOWED(927),
    INACTIVE_USER(935),
    NULL_REQUEST_RECEIVED(920),


    // HttpStatus Codes 1**to **
    CONTINUE(100),
    SWITCHING_PROTOCOLS(101),
    PROCESSING(102),

    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NON_AUTHORITATIVE_INFORMATION(203),
    NO_CONTENT(204),
    RESET_CONTENT(205),
    PARTIAL_CONTENT(206),
    MULTI_STATUS(207),

    MULTIPLE_CHOICES(300),
    MOVED_PERMANENTLY(301),
    MOVED_TEMPORARILY(302),
    SEE_OTHER(303),
    NOT_MODIFIED(304),
    USE_PROXY(305),
    TEMPORARY_REDIRECT(307),

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    PROXY_AUTHENTICATION_REQUIRED(407),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    GONE(410),
    LENGTH_REQUIRED(411),
    PRECONDITION_FAILED(412),
    REQUEST_TOO_LONG(413),
    REQUEST_URI_TOO_LONG(414),
    UNSUPPORTED_MEDIA_TYPE(415),
    REQUESTED_RANGE_NOT_SATISFIABLE(416),
    EXPECTATION_FAILED(417),
    INSUFFICIENT_SPACE_ON_RESOURCE(419),
    METHOD_FAILURE(420),
    UNPROCESSABLE_ENTITY(422),
    LOCKED(423),
    FAILED_DEPENDENCY(424),

    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504),
    HTTP_VERSION_NOT_SUPPORTED(505),
    INSUFFICIENT_STORAGE(507);


    private int code;

    private ApplicationResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage(){
        final String codeToString = this.toString();
        return toSentence(codeToString);
    }

    public static void main(String[] args) {
        System.out.println(MULTIPLE_CHOICES.getMessage());
    }
}
