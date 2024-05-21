package com.tawandr.utils.messaging.dtos;

import com.tawandr.utils.messaging.enums.ApplicationResponseCode;
import lombok.Builder;

import java.util.*;

@Builder
public class StandardResponse<T> {
    private boolean success;
    private String narrative;
    private int responseCode;
    private List<T> result;

    public StandardResponse(T t) {
        result = new ArrayList<>();
        result.add(t);
    }

    public StandardResponse(Collection<T> list) {
        this(new ArrayList<>(list));
    }

    public StandardResponse(List<T> list) {
        setResult(list);
    }

    public StandardResponse(boolean success,
                            String narrative,
                            int responseCode,
                            List<T> result) {
        this.success = success;
        this.narrative = narrative;
        this.responseCode = responseCode;
        this.result = result;
    }

    public void setResult(T t) {
        if (result == null) {
            result = new ArrayList<>();
        }
        result.add(t);
    }

    public void setResult(List<T> list) {
        result = list;
    }

    public static <T> StandardResponse<T> setMainSuccessResponse(T t) {
        final StandardResponse<T> response = new StandardResponse<>(t);
        return setMainSuccessResponse(response);
    }

    public static <T> StandardResponse<T> setMainSuccessResponse(List<T> list) {
        final StandardResponse<T> response = new StandardResponse<>(list);
        return setMainSuccessResponse(response);
    }

    public static <T> StandardResponse<T> setMainSuccessResponse(StandardResponse<T> response) {
        final ApplicationResponseCode responseCode = ApplicationResponseCode.SUCCESS;
        response.setResponseCode(responseCode.getCode());
        response.setNarrative(responseCode.toString());
        response.setSuccess(true);
        return response;
    }

    public static <T> StandardResponse<T> setFailureResponse(T request, ApplicationResponseCode responseCode, String errorMessage) {
        return setFailureResponse(Collections.singletonList(request), responseCode, errorMessage);
    }

    public static <T> StandardResponse<T> setFailureResponse(List<T> request, ApplicationResponseCode responseCode, String errorMessage) {
        StandardResponse<T> response = new StandardResponse<>(request);
        response.setResponseCode(responseCode.getCode());
        response.setNarrative(errorMessage);
        response.setSuccess(false);
        return response;
    }

    public static <T> StandardResponse<T> setFailureResponse(T request, ApplicationResponseCode responseCode) {
        return setFailureResponse(Collections.singletonList(request), responseCode);
    }

    public static <T> StandardResponse<T> setFailureResponse(List<T> request, ApplicationResponseCode responseCode) {
        return setFailureResponse(request, responseCode, responseCode.toString());
    }

    public static <T> StandardResponse<T> setFailureResponse(T request, String errorMessage) {
        return setFailureResponse(Collections.singletonList(request), errorMessage);
    }

    public static <T> StandardResponse<T> setFailureResponse(List<T> request, String errorMessage) {
        return setFailureResponse(request, ApplicationResponseCode.TRANSACTION_FAILED, errorMessage);
    }

    public static <T> StandardResponse<T> setFailureResponse(T request) {
        return setFailureResponse(request, ApplicationResponseCode.TRANSACTION_FAILED);
    }

    public static <T> StandardResponse<T> setNullRequestObjectResponse(T request) {
        final String errorMessage = ApplicationResponseCode.NULL_REQUEST_RECEIVED.toString();
        return StandardResponse.setFailureResponse(request, errorMessage);
    }

    public static <T> StandardResponse setFailureResponse(Class<T> clazz) {
        T request;
        try {
            request = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            request = null;
        }
        return setFailureResponse(request);
    }

    public String standardToString() {
        return toString() + "\n";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<T> getResult() {
        return result;
    }

    public boolean empty() {
        return result == null || result.isEmpty();
    }

    @Override
    public String toString() {
        return "\n\tStandardResponse{" +
                " \n\t\tsuccess=" + success +
                ", \n\t\tnarrative='" + narrative + '\'' +
                ", \n\t\tresponseCode='" + responseCode + '\'' +
                ", \n\t\tresult=" + result +
                "\n\t}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StandardResponse<?> that = (StandardResponse<?>) o;
        return success == that.success &&
                Objects.equals(responseCode, that.responseCode) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), success, responseCode, result);
    }
}
