package com.tawandr.utils.crud.api.processors;

import com.tawandr.utils.crud.api.configurations.CrudConfigurations;
import com.tawandr.utils.crud.business.CrudService;
import com.tawandr.utils.crud.domain.BaseEntity;
import com.tawandr.utils.crud.domain.BaseWrapper;
import com.tawandr.utils.messaging.dtos.ApiRequestMetaData;
import com.tawandr.utils.messaging.dtos.StandardRequest;
import com.tawandr.utils.messaging.dtos.StandardResponse;
import com.tawandr.utils.messaging.exceptions.NullRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ComponentScan(basePackageClasses = {CrudConfigurations.class})
@RequiredArgsConstructor
public class CrudProcessor<Entity extends BaseEntity>{
    protected final CrudService<Entity> crudService;
    protected final CrudConverter<Entity> converter;

    public StandardResponse<Entity> create(final Class<Entity> entityClass, final List<Entity> dtos,
                                        final ApiRequestMetaData requestMetaData) {
        preProcess(dtos, requestMetaData);
        final StandardRequest<Entity> standardRequest = converter.convertToStandardRequest(dtos, requestMetaData);
        final StandardResponse<Entity> saved = crudService.save(entityClass, standardRequest);
        return converter.convertToStandardResponse(saved);
    }

    public StandardResponse<Entity> update(final Class<Entity> entityClass, final List<Entity> dtos,
                                        final ApiRequestMetaData requestMetaData) {
        preProcess(dtos, requestMetaData);
        final StandardRequest<Entity> standardRequest = converter.convertToStandardRequest(dtos, requestMetaData);
        final StandardResponse<Entity> updated = crudService.update(entityClass, standardRequest);
        return converter.convertToStandardResponse(updated);
    }

    
    public StandardResponse<Entity> delete(final Class<Entity> entityClass, final List<Long> idList,
                                        final ApiRequestMetaData requestMetaData) {
        preProcess(idList, requestMetaData);
        final StandardResponse<Entity> deleted = crudService.delete(entityClass, idList, requestMetaData);
        return converter.convertToStandardResponse(deleted);
    }

    
    public StandardResponse<Entity> findById(final Class<Entity> entityClass, final Long id,
                                          final ApiRequestMetaData requestMetaData) {
        preProcess(id, requestMetaData);
        final StandardRequest<Long> standardRequest = StandardRequest
                .<Long>builder()
                .request(id)
                .apiRequestMetaData(requestMetaData)
                .build();
        final StandardResponse<Entity> foundById = crudService.findById(entityClass, standardRequest);
        return converter.convertToStandardResponse(foundById);
    }

    
    public StandardResponse<Entity> findAll(final Class<Entity> entityClass, final ApiRequestMetaData requestMetaData) {
        preProcess(requestMetaData, requestMetaData);
        final StandardResponse<Entity> all = crudService.findAll(entityClass, requestMetaData);
        return converter.convertToStandardResponse(all);
    }

    
    public <W extends BaseWrapper> StandardResponse<Entity> find(final Class<Entity> entityClass, final W wrapper,
                                                              final ApiRequestMetaData requestMetaData) {
        preProcess(wrapper, requestMetaData);
        final StandardRequest<W> standardRequest = converter.convertToStandardRequest(wrapper, requestMetaData);
        final StandardResponse<Entity> found = crudService.find(entityClass, standardRequest);
        return converter.convertToStandardResponse(found);
    }

    protected <T> void preProcess(final T request,
                                  final ApiRequestMetaData requestMetaData) {
        if (request == null) {
            throw new NullRequestException();
        }
        if (requestMetaData == null) {
            throw new NullRequestException(ApiRequestMetaData.class);
        }
    }
}
