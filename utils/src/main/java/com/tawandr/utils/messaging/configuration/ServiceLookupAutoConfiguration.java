package com.tawandr.utils.messaging.configuration;

import com.tawandr.utils.messaging.servicelookup.ServiceLookup;
import com.tawandr.utils.messaging.servicelookup.SpringContextServiceLookupImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ServiceLookup.class})
public class ServiceLookupAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ServiceLookup serviceLookup(ApplicationContext applicationContext) {
		return new SpringContextServiceLookupImpl(applicationContext);
	}
}
