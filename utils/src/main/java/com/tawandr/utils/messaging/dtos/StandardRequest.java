package com.tawandr.utils.messaging.dtos;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class StandardRequest<T> {
    @Nullable
    private Long parentId;
    private List<T> request;
    private ApiRequestMetaData apiRequestMetaData;

    public StandardRequest(List<T> requestList, ApiRequestMetaData apiRequestMetaData) {
        this.request = requestList;
        this.apiRequestMetaData = apiRequestMetaData;
    }

    public StandardResponse<T> processRequestElements(final Function<T, T> function) {
        final List<T> processedList = request
                .stream()
                .map(function)
                .collect(Collectors.toList());
        return StandardResponse.setMainSuccessResponse(processedList);
    }



    public void setRequest(T request) {
        this.request = Collections.singletonList(request);
    }

    @SuppressWarnings("unchecked")
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        public StandardRequest<T> build() {
            return new StandardRequest<>(request, apiRequestMetaData);
        }

        private final List<T> request = new ArrayList<>(1);
        private ApiRequestMetaData apiRequestMetaData;

        public Builder<T> request(T request) {
            this.request.add(request);
            return this;
        }

        public Builder<T> request(Collection<T> request) {
            return request(new ArrayList<>(request));
        }

        public Builder<T> request(List<T> request) {
            this.request.addAll(request);
            return this;
        }

        public Builder<T> apiRequestMetaData(ApiRequestMetaData apiRequestMetaData) {
            this.apiRequestMetaData = apiRequestMetaData;
            return this;
        }
    }

    public void setRequest(List<T> request) {
        this.request = request;
    }

    public void setApiRequestMetaData(ApiRequestMetaData apiRequestMetaData) {
        this.apiRequestMetaData = apiRequestMetaData;
    }


    @Override
    public String toString() {
        return "\n\tStandardRequest{" +
                " \n\t\trequest=" + request +
                ", \n\t\tapiRequestMetaData=" + apiRequestMetaData +
                "\n\t}";
    }
}
