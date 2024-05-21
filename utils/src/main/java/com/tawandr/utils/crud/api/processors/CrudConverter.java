package com.tawandr.utils.crud.api.processors;

import com.tawandr.utils.crud.domain.BaseWrapper;
import com.tawandr.utils.messaging.dtos.ApiRequestMetaData;
import com.tawandr.utils.messaging.dtos.StandardRequest;
import com.tawandr.utils.messaging.dtos.StandardResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By Dougie T Muringani : 26/5/2020
 */
@Service
public class CrudConverter<Entity> {
    // Todo: [26/5/2020]{@tmuringani} : Create an Aspect to handle null requests
    public Entity convertToDto(final Entity entity){
        return entity;
    };

    public Entity convertToEntity(final Entity dto){
        return dto;
    }

    public Collection<Entity> convertToDtoCollection(final Collection<Entity> entities){
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Collection<Entity> convertToEntityCollection(final Collection<Entity> dtos){
        return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    public StandardResponse<Entity> convertToStandardResponse(final StandardResponse<Entity> standardResponse){
        final List<Entity> entities = standardResponse.getResult();
        final Collection<Entity> dtos = convertToDtoCollection(entities);

        return StandardResponse.<Entity>builder()
                .result(new ArrayList<>(dtos))
                .success(standardResponse.isSuccess())
                .responseCode(standardResponse.getResponseCode())
                .narrative(standardResponse.getNarrative())
                .build();
    }

    public <W extends BaseWrapper> StandardRequest<W> convertToStandardRequest(final W wrapper,
                                                                               final ApiRequestMetaData apiRequestMetaData){
        return StandardRequest
                .<W>builder()
                .request(wrapper)
                .apiRequestMetaData(apiRequestMetaData)
                .build();
    }

    public StandardRequest<Entity> convertToStandardRequest(final Collection<Entity> dtos,
                                                            final ApiRequestMetaData apiRequestMetaData){
        final Collection<Entity> entities = convertToEntityCollection(dtos);
        return StandardRequest
                .<Entity>builder()
                .request(entities)
                .apiRequestMetaData(apiRequestMetaData)
                .build();
    }
}