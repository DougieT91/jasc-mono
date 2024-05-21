package com.tawandr.utils.crud.api.rest.resources;

import com.tawandr.utils.crud.api.configurations.CrudConfigurations;
import com.tawandr.utils.crud.api.processors.CrudProcessor;
import com.tawandr.utils.crud.domain.BaseEntity;
import com.tawandr.utils.crud.domain.BaseWrapper;
import com.tawandr.utils.messaging.dtos.ApiRequestMetaData;
import com.tawandr.utils.messaging.dtos.StandardResponse;
import com.tawandr.utils.rest.endpoint.RestRequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@ComponentScan(basePackageClasses = {CrudConfigurations.class})
public abstract class CrudResource<Entity extends BaseEntity> {
    @Autowired
    protected CrudProcessor<Entity> processor;
    public abstract Class<Entity> getEntityClass();
    //--------------------------------------------- DefaultPageResource ---------------------------------------------//
    @Operation(description = "An Endpoint to test if the APIs are reachable", summary = "Test API reachability")
    @GetMapping("test")
    public HttpEntity<List<String>> test() {
        return RestRequestUtils.createEntity(Collections.singletonList("SUCCESS"), HttpStatus.OK.value());
    }

    @Operation(description = "The API Landing Page. Also to test the API", summary = "API Landing Page")
    @GetMapping
    public Object defaultPage() {
        final StandardResponse<Entity> all = processor.findAll(getEntityClass(), ApiRequestMetaData.builder().build());
        return all.getResult();
    }

    //----------------------------------------------- CreatorResource -----------------------------------------------//

    @Operation(description = "Endpoint to Create a new a record", summary = "Create Record")
    @PostMapping
    public HttpEntity<StandardResponse<Entity>> add(
            @RequestBody List<Entity> dtos,
            final Principal principal
    ) {
        final ApiRequestMetaData requestMetaData = ApiRequestMetaData.builder()
                .principal(principal)
                .build();

        logRequest("ADD", requestMetaData, dtos);
        final StandardResponse<Entity> standardResponse = processor.create(getEntityClass(), dtos, requestMetaData);
        final HttpEntity<StandardResponse<Entity>> entity = RestRequestUtils.createEntity(standardResponse);
        logResponse(entity);
        return entity;
    }


    @Operation(description = "Endpoint to Update an existing record", summary = "Update Record")
    @PutMapping
    public HttpEntity<StandardResponse<Entity>> update(
            @RequestBody List<Entity> dtos,
            final Principal principal
    ) {
        final ApiRequestMetaData requestMetaData = ApiRequestMetaData.builder()
                .principal(principal)
                .build();

        logRequest("UPDATE", requestMetaData, dtos);
        final StandardResponse<Entity> standardResponse = processor.update(getEntityClass(), dtos, requestMetaData);
        final HttpEntity<StandardResponse<Entity>> entity = RestRequestUtils.createEntity(standardResponse);
        logResponse(entity);
        return entity;
    }


    //----------------------------------------------- FinderResource. -----------------------------------------------//

    @Operation(description = "Endpoint to get a record by its ID", summary = "Find record by ID")
    @GetMapping("{id}")
    public HttpEntity<StandardResponse<Entity>> findById(
            @PathVariable final Long id,
            final Principal principal
    ) {
        final ApiRequestMetaData requestMetaData = ApiRequestMetaData.builder()
                .principal(principal)
                .build();

        logRequest("FIND-ONE", requestMetaData, id);
        final StandardResponse<Entity> standardResponse = processor.findById(getEntityClass(), id, requestMetaData);
        final HttpEntity<StandardResponse<Entity>> entity = RestRequestUtils.createEntity(standardResponse);
        logResponse(entity);
        return entity;
    }

    @Operation(description = "Endpoint to get a record by its Attributes", summary = "Search for record")
    @GetMapping("search")
    public <W extends BaseWrapper>
    HttpEntity<StandardResponse<Entity>> find(@RequestParam final W wrapper, final Principal principal) {
        final ApiRequestMetaData requestMetaData = ApiRequestMetaData.builder()
                .principal(principal)
                .build();

        logRequest("SEARCH", requestMetaData, wrapper);
        final StandardResponse<Entity> standardResponse = processor.find(getEntityClass(), wrapper, requestMetaData);
        final HttpEntity<StandardResponse<Entity>> entity = RestRequestUtils.createEntity(standardResponse);
        logResponse(entity);
        return entity;
    }

    @Operation(description = "Endpoint to get all records", summary = "Find all records")
    @GetMapping("all")
    public HttpEntity<StandardResponse<Entity>> findAll() {
        final ApiRequestMetaData requestMetaData = ApiRequestMetaData.builder().build();

        logRequest("FIND-ALL", requestMetaData);
        final StandardResponse<Entity> standardResponse = processor.findAll(getEntityClass(), requestMetaData);
        final HttpEntity<StandardResponse<Entity>> entity = RestRequestUtils.createEntity(standardResponse);
        logResponse(entity);
        return entity;
    }

    //----------------------------------------------- DeleterResource -----------------------------------------------//

    @Operation(description = "Delete Multiple Records using their IDs", summary = "Delete By IDs")
    @DeleteMapping
    public HttpEntity<StandardResponse<Entity>> deleteMany(@RequestBody List<Long> idList, final Principal principal) {
        final ApiRequestMetaData requestMetaData = ApiRequestMetaData
                .builder()
                .principal(principal)
                .build();

        logRequest("DELETE-MANY", requestMetaData);
        final StandardResponse<Entity> standardResponse = processor.delete(getEntityClass(), idList, requestMetaData);
        final HttpEntity<StandardResponse<Entity>> entity = RestRequestUtils.createEntity(standardResponse);
        logResponse(entity);
        return entity;
    }

    @Operation(description = "Delete Single Records using its ID", summary = "Delete One By ID")
    @DeleteMapping("{id}")
    public HttpEntity<StandardResponse<Entity>> delete(@PathVariable final Long id, final Principal principal) {
        final ApiRequestMetaData requestMetaData = ApiRequestMetaData
                .builder()
                .principal(principal)
                .build();

        logRequest("DELETE-ONE", requestMetaData);

        final StandardResponse<Entity> standardResponse = processor.delete(getEntityClass(), Collections.singletonList(id), requestMetaData);
        final HttpEntity<StandardResponse<Entity>> entity = RestRequestUtils.createEntity(standardResponse);
        logResponse(entity);
        return entity;
    }

    protected static void logRequest(final String verb,
                                     final ApiRequestMetaData requestMetaData,
                                     final Object... parameters) {
        log.debug("Received Request to [{}] record(s):...", verb);
        final HashMap<String, Object> parameterMap = new HashMap<>();

        parameterMap.put("Language", requestMetaData.getLanguage());
        parameterMap.put("SourceType", requestMetaData.getSourceType());
        parameterMap.put("SourceName", requestMetaData.getSourceName());
        parameterMap.put("UserName", requestMetaData.getUserName());

        if (parameters != null && parameters.length > 0) {
            Arrays.asList(parameters)
                    .forEach(parameter -> parameterMap.put(parameter.getClass().getSimpleName(), parameter));
        }
        log.debug("parameters: {}", parameterMap);
    }

    protected static <Entity> void logResponse(HttpEntity<StandardResponse<Entity>> entity) {
        final StandardResponse<Entity> body = entity.getBody();
        if (body != null) {
            log.debug("Request Processing Completed with response: [response-code: {}, narrative: {}, elements: {}]",
                    body.getResponseCode(), body.getNarrative(), body.getResult().size());
        }
    }

}