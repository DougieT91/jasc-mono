package com.tawandr.utils.messaging.enums;

public enum I18Code {
    MESSAGE_ACTION_CREATE_FAQ("messages.action.created.faq"),
    MESSAGE_FAQ_CREATED_SUCCESSFULLY("messages.faq.created.successfully"),
    MESSAGE_FAQ_CREATION_FAILED("messages.faq.creation.failed"),
    MESSAGE_VALIDATION_SUCCESS("message.validation.success"),

    MESSAGE_REQUEST_NOT_SUPPORTED("message.request.not.supported");

    private String code;

    private I18Code(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
