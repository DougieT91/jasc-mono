package com.tawandr.utils.crud.business;

import com.tawandr.utils.crud.api.configurations.CrudConfigurations;
import com.tawandr.utils.crud.domain.BaseEntity;
import com.tawandr.utils.crud.domain.BaseWrapper;
import com.tawandr.utils.messaging.dtos.ApiRequestMetaData;
import com.tawandr.utils.messaging.dtos.StandardRequest;
import com.tawandr.utils.messaging.dtos.StandardResponse;
import com.tawandr.utils.messaging.enums.EntityStatus;
import com.tawandr.utils.messaging.exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created By Dougie T Muringani : 10/5/2020
 */
@Slf4j
@Service
@Repository
@Transactional
@ComponentScan(basePackageClasses = {CrudConfigurations.class})
public class CrudService<Entity extends BaseEntity> {

    @Autowired
    private EntityManager entityManager;

    public CrudService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CrudService() {
    }

    public <Parent extends BaseEntity> StandardResponse<Entity> save(final Class<Entity> mainEntityClass,
                                                                     final StandardRequest<Entity> standardRequest,
                                                                     final Class<Parent> parentEntityClass) {
        validate(standardRequest);

        final CrudService<Parent> parentCrudService = new CrudService<>(entityManager);
        final Parent parent = parentCrudService.findById(parentEntityClass, standardRequest.getParentId());
        final List<Entity> entityList = standardRequest.getRequest();
        for (Entity child : entityList) {
            setChildField(child, parent, mainEntityClass, parentEntityClass);
        }
        return save(mainEntityClass, standardRequest);
    }

    @SneakyThrows
    public <Parent extends BaseEntity> void setChildField(final Entity child,
                                                          final Parent parent,
                                                          final Class<Entity> childClass,
                                                          final Class<Parent> fieldType) {
        final Entity childField = getChildEntity(parent, fieldType, childClass);
        final Field field = findField(childClass, fieldType);

        field.setAccessible(true);
        field.set(childField, child);
    }

    @SneakyThrows
    private <Parent extends BaseEntity> Entity getChildEntity(Parent parent,
                                                              Class<Parent> parentClass,
                                                              Class<Entity> childClass) {
        // Use reflection to get child property on parent
        final String childClassName = childClass.getSimpleName();
        final Field childField = parentClass.getDeclaredField(childClassName);
        return (Entity) childField.get(parent);
    }

    private Field findField(Class<?> childClass, Class<?> fieldType) {
        // Loop through fields to find match by type
        for (Field field : childClass.getDeclaredFields()) {
            if (field.getType() == fieldType) {
                return field;
            }
        }
        throw new RuntimeException("No matching field found");
    }

    public StandardResponse<Entity> save(final Class<Entity> entityClass, final StandardRequest<Entity> standardRequest) {
        validate(standardRequest);
        return standardRequest.processRequestElements(entity -> save(entityClass, entity));
    }

    public StandardResponse<Entity> update(final Class<Entity> entityClass, StandardRequest<Entity> standardRequest) {
        validate(standardRequest);
        return standardRequest.processRequestElements(entity -> update(entityClass, entity));
    }

    public StandardResponse<Entity> findAll(final Class<Entity> entityClass, ApiRequestMetaData apiRequestMetaData) {
        validate(StandardRequest.<Entity>builder().apiRequestMetaData(apiRequestMetaData).build());
        final List<Entity> found;
        try {
            log.trace("finding All records");
            final SimpleJpaRepository<Entity, Long> repository = new SimpleJpaRepository<>(entityClass, entityManager);
            found = repository.findAll();
        } catch (Exception e) {
            final String errorMessage = "Failed to find (All) Records.%nReason: %s".formatted( e.getMessage());
            log.error(errorMessage);
            throw new BusinessException(errorMessage, e);
        }
        log.trace("found: {}", found);
        return StandardResponse.setMainSuccessResponse(found);
    }

    public StandardResponse<Entity> findById(final Class<Entity> entityClass, StandardRequest<Long> standardRequest) {
        validate(
                StandardRequest.<Entity>builder()
                        .apiRequestMetaData(standardRequest.getApiRequestMetaData())
                        .build()
        );

        final List<Entity> found = standardRequest
                .getRequest()
                .stream()
                .map(id -> findById(entityClass, id))
                .collect(Collectors.toList());

        log.trace("found: {}", found);
        return StandardResponse.setMainSuccessResponse(found);
    }

    protected Entity findById(final Class<Entity> entityClass, Long id) {
        final Optional<Entity> find;
        try {
            log.trace("finding single record by [id: {}]", id);
            final SimpleJpaRepository<Entity, Long> repository = new SimpleJpaRepository<>(entityClass, entityManager);
            find = repository.findById(id);
        } catch (Exception e) {
            final String errorMessage = String.format("Failed to find (One) Record.%nReason: %s", e.getMessage());
            log.error(errorMessage);
            throw new RecordNotFoundException(id, errorMessage);
        }
        final Entity found = find.orElse(null);
        if (found == null || found.getEntityStatus() == EntityStatus.DELETED) {
            throw new RecordNotFoundException(id, "Record [id: " + id + "] to does not exist");
        }
        return found;
    }

    public StandardResponse<Entity> findByDate(final Class<Entity> entityClass, StandardRequest<LocalDateTime> standardRequest) {
        validate(StandardRequest.<Entity>builder().apiRequestMetaData(standardRequest.getApiRequestMetaData()).build());
        final LocalDateTime dateTime = standardRequest.getRequest().get(0);
        final List<Entity> found;
        try {
            log.trace("finding multiple records by [date: {}]", dateTime.toLocalDate());
            found = findByCreationDate(entityClass, dateTime);
            checkIfRecordsWereFound(dateTime, found);
        } catch (Exception e) {
            final String message = "Failed to find any Records matching specified creation Date.";
            final String errorMessage = String.format("%s%nReason: %s", message, e.getMessage());
            log.error(errorMessage);
            throw new BusinessException(errorMessage, e);
        }
        log.trace("found: {}", found);
        return StandardResponse.setMainSuccessResponse(found);
    }

    private List<Entity> findByCreationDate(final Class<Entity> entityClass, LocalDateTime dateTime) {
        final LocalDateTime dayStart = dateTime.toLocalDate().atTime(LocalTime.MIN);
        final LocalDateTime dayEnd = dateTime.toLocalDate().atTime(LocalTime.MAX);

        final SimpleJpaRepository<Entity, Long> repository = new SimpleJpaRepository<>(entityClass, entityManager);
        final List<Entity> all = repository.findAll();
        return all
                .stream()
                .filter(e -> e.getCreatedOn().isAfter(dayStart) && e.getCreatedOn().isBefore(dayEnd))
                .collect(Collectors.toList());
    }


    public <Wrapper extends BaseWrapper> StandardResponse<Entity> find(final Class<Entity> entityClass, StandardRequest<Wrapper> wrapperRequest) {
        final List<Wrapper> wrappers = wrapperRequest.getRequest();
        log.trace("finding multiple records by search criteria:{}", wrapperRequest);
        final List<Entity> found = wrappers
                .stream()
                .map(wrapper -> findByWrapper(entityClass, wrapper))
                .flatMap(sr -> sr.getResult().stream())
                .collect(Collectors.toList());
        log.trace("found: {}", found);
        return StandardResponse.setMainSuccessResponse(found);
    }

    public StandardResponse<Entity> delete(final Class<Entity> entityClass, StandardRequest<Entity> standardRequest) {
        final List<Long> idList = standardRequest.getRequest()
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        return delete(entityClass, idList, standardRequest.getApiRequestMetaData());
    }

    public StandardResponse<Entity> delete(final Class<Entity> entityClass, final List<Long> idList,
                                           final ApiRequestMetaData apiRequestMetaData) {
        validate(apiRequestMetaData);
        final StandardRequest<Long> findByIdStandardRequest = StandardRequest
                .<Long>builder()
                .request(idList)
                .apiRequestMetaData(apiRequestMetaData)
                .build();

        final StandardResponse<Entity> foundEntities;
        try {
            foundEntities = findById(entityClass, findByIdStandardRequest);
        } catch (RecordNotFoundException e) {
            final String errorMessage = String.format("Record(s) Deletion Failed. Reason: %s ", e.getMessage());
            log.error(errorMessage);
            throw new RecordNotFoundException(e.getFirstIdentifier(), errorMessage);
        }

        final StandardRequest<Entity> standardRequest = StandardRequest
                .<Entity>builder()
                .request(foundEntities.getResult())
                .apiRequestMetaData(apiRequestMetaData)
                .build();

        return standardRequest.processRequestElements(entity -> delete(entityClass, entity));
    }

    protected Entity save(final Class<Entity> entityClass, final Entity entity) {
        log.trace("Saving Record: {}", entity);
        entity.init();
        final SimpleJpaRepository<Entity, Long> repository = new SimpleJpaRepository<>(entityClass, entityManager);
        final Entity saved = repository.save(entity);
        log.trace("saved record [id: {}]", saved.getId());
        return saved;
    }

    protected Entity update(final Class<Entity> entityClass, Entity entity) {
        final Long id = entity.getId();
        final StandardResponse<Entity> findResponse = findById(entityClass, StandardRequest.<Long>builder().request(id).build());
        if (!findResponse.isSuccess()) {
            final String errorMessage = String.format("Record [id: %s] to be Updated does not exist", entity.getId());
            log.error(errorMessage);
            throw new RecordNotFoundException(id, errorMessage);
        }
        log.trace("Record [id: {}] Found. Updating Record with: {}", entity.getId(), entity);
        entity.reload();
        final Entity updated = save(entityClass, entity);
        log.trace("Updated record [id: {}]", updated.getId());
        return updated;
    }

    protected Entity delete(final Class<Entity> entityClass, final Entity entity) {
        log.trace("Deleting Record:{}", entity);
        entity.setEntityStatus(EntityStatus.DELETED);
        final SimpleJpaRepository<Entity, Long> repository = new SimpleJpaRepository<>(entityClass, entityManager);
        final Entity deleted = repository.save(entity);
        log.trace("Deleted record [id: {}]", deleted.getId());
        return deleted;
    }


    public <Wrapper extends BaseWrapper> StandardResponse<Entity> findByWrapper(final Class<Entity> entityClass, Wrapper wrapper) {
        // Todo: Implement this
        return null;
    }

    ;

    public <Wrapper extends BaseWrapper> StandardResponse<Entity> defaultFindByWrapper(final Class<Entity> entityClass, Wrapper wrapper) {
        StandardResponse<Entity> standardResponse = new StandardResponse<>(Collections.emptyList());
        if (wrapper == null) {
            standardResponse.setResult(Collections.emptyList());
            standardResponse.setResponseCode(404);
            return standardResponse;
        }
        final Long id = wrapper.getId();
        if (id != null) {
            return findById(entityClass, StandardRequest.<Long>builder().request(id).build());
        } else throw new MethodNotImplementedException("The search method has not yet been Implemented");
    }

    protected void validate(StandardRequest<Entity> standardRequest) {
        final ApiRequestMetaData apiRequestMetaData = standardRequest.getApiRequestMetaData();
        final StandardResponse<ApiRequestMetaData> validation = validate(apiRequestMetaData);
        if (validation != null && !validation.isSuccess()) {
            final String errorMessage = String.format("Validation failed for request:\n%s\n", validation.getResult());
            throw new ValidationException(errorMessage, validation.getNarrative());
        }
    }

    protected StandardResponse<ApiRequestMetaData> validate(ApiRequestMetaData apiRequestMetaData) {
        // Todo: Implement this
        return null;
    }

    private void checkIfRecordsWereFound(Object identifier,
                                         List<Entity> findList) {
        if (findList == null || findList.isEmpty()) {
            throw new RecordNotFoundException(
                    "Object",
                    RecordIdentifier.builder().withKey("creationDate", identifier).build(), "record not found"
            );
        }
    }
}
