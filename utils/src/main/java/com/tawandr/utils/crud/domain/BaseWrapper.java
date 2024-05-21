package com.tawandr.utils.crud.domain;

import com.tawandr.utils.messaging.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
public class BaseWrapper {
    protected Long id;

    protected String status;
    protected EntityStatus entityStatus;
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected LocalDateTime creationDate;
}
