package com.tawandr.utils.crud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tawandr.utils.messaging.enums.EntityStatus;
import com.tawandr.utils.messaging.keygen.KeyGen;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Setter
@Getter
@Slf4j
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonIgnore
    @Transient
    private long version;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    protected EntityStatus entityStatus;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createdOn;

    @JsonIgnore
    @LastModifiedDate
    protected LocalDateTime modifiedOn;

    @PrePersist
    public void init() {
        if (id == null || id == 0L) {
            id = KeyGen.getUniqueId();
            log.debug("Set Entity id: {}", getId());
        }

        if (entityStatus == null) {
            entityStatus = EntityStatus.ACTIVE;
        }

        if (createdOn == null) {
            createdOn = now();
        }
        log.debug("Entity created [id: {}]", getId());
        log.trace("{}",this);
        secondaryPreCreate();
    }

    protected abstract void secondaryPreCreate();

    protected abstract void secondaryPreUpdate();

    @PreUpdate
    public void reload() {
        modifiedOn = now();
        log.debug("Entity updated [id: {}]", getId());
        log.trace("{}",this);
    }

    public BaseEntity() {
    }

    public String getName() {
        return getClass().getSimpleName();
    }



    @Override
    public String toString() {
        return "\n\tBaseEntity{" +
                " \n\t\tid=" + id +
                ", \n\t\tversion=" + version +
                ", \n\t\tentityStatus=" + entityStatus +
                ", \n\t\tcreationDate=" + createdOn +
                ", \n\t\tdateLastUpdated=" + modifiedOn +
                "\n\t}";
    }
}
