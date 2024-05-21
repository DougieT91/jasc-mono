package com.tawandr.utils.messaging.dtos;

import com.tawandr.utils.messaging.enums.RequestOperation;

/**
 * Created By Dougie T Muringani : 14/12/2018
 */
public class ResponseUtils {
    public static <T> void setMainSuccess(StandardResponse<T> response, RequestOperation operation) {
        response.setSuccess(true);
        response.setResponseCode(200);
        response.setNarrative(setMainSuccessNarrative(operation));
    }

    private static String setMainSuccessNarrative(RequestOperation operation) {
        String action;
        switch (operation) {
            case CREATE:
                action = "CREATED";
                break;
            case READ:
                action = "READ";
                break;
            case UPDATE:
                action = "UPDATED";
                break;
            case DELETE:
                action = "DELETED";
                break;
            default:
                return "Success";
        }
        return "Record(s) " + action + " Successfully";
    }

    private ResponseUtils() {
        super();
    }
}
