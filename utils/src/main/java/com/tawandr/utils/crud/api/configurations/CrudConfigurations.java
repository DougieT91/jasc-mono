package com.tawandr.utils.crud.api.configurations;

import com.tawandr.utils.crud.api.processors.CrudProcessor;
import com.tawandr.utils.crud.api.rest.advice.CrudUtilsAdviceMarkerInterface;
import com.tawandr.utils.crud.business.CrudService;
import com.tawandr.utils.crud.business.aspect.CrudUtilsAspectMarkerInterface;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        JpaRepository.class,
})
@ComponentScan(basePackageClasses = {
        CrudUtilsAdviceMarkerInterface.class,
        CrudUtilsAspectMarkerInterface.class,
        CrudProcessor.class,
        CrudService.class
})
@Import(WebConfig.class)
public class CrudConfigurations {}
