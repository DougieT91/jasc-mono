package com.tawandr.utils.crud.business.aspect;

import com.tawandr.utils.messaging.dtos.ApiRequestMetaData;
import com.tawandr.utils.messaging.dtos.StandardRequest;
import com.tawandr.utils.messaging.dtos.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class CrudServiceAspect {

    private static final String suffix = ") ";
    private static final String classPath = "execution(public * com.tawandr.utils.crud.business.CrudService";
    private static final String aroundValuePrefix = classPath + ".*(..)) && args(";
    private static final String aroundStandardRequest = aroundValuePrefix + "standardRequest" + suffix;
    private static final String aroundWrapperRequest = aroundValuePrefix + "wrapperRequest" + suffix;
    private static final String aroundFindAllRequest = aroundValuePrefix + "apiRequestMetaData" + suffix;


    @Around(aroundStandardRequest)
    protected <Entity> StandardResponse<Entity> handleStandardRequest(final ProceedingJoinPoint joinPoint,
                                                                      final StandardRequest<Entity> standardRequest) throws Throwable {
        return processRequest(joinPoint, standardRequest);
    }

    @Around(aroundWrapperRequest)
    protected <Wrapper> StandardResponse<Wrapper> handleWrapperRequest(final ProceedingJoinPoint joinPoint,
                                                                       final StandardRequest<Wrapper> wrapperRequest) throws Throwable {
        return processRequest(joinPoint, wrapperRequest);
    }

    @Around(aroundFindAllRequest)
    protected <ARM> StandardResponse<ARM> handleFindAllRequest(final ProceedingJoinPoint joinPoint,
                                                               final ApiRequestMetaData apiRequestMetaData) throws Throwable {
        StandardRequest<ARM> standardRequest = new StandardRequest<>(null, apiRequestMetaData);
        return processRequest(joinPoint, standardRequest);
    }


    private <T> StandardResponse<T> processRequest(final ProceedingJoinPoint joinPoint,
                                                   final StandardRequest<T> standardRequest) throws Throwable {
        StandardResponse<T> standardResponse;
        final List<T> request = standardRequest.getRequest();
        final ApiRequestMetaData requestMetaData = standardRequest.getApiRequestMetaData();
        final String method =  joinPoint.getSignature().getName() + "(...)";

        try {
            log.trace("Received Standard Request on [method: {}] :\n{}", method, standardRequest);
            standardResponse = (StandardResponse<T>) joinPoint.proceed();
            log.trace("Returning Standard Response from  [method: {}]:\n{}", method, standardResponse);
            return standardResponse;
        } catch (Exception e) {
            final String errorMessage = e.getMessage();
            log.error(errorMessage);
            throw e;
        }
    }
}
