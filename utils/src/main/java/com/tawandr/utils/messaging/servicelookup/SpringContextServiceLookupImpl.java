package com.tawandr.utils.messaging.servicelookup;

import com.tawandr.utils.messaging.exceptions.ConfigurationException;
import com.tawandr.utils.messaging.exceptions.ExceptionUtil;
import com.tawandr.utils.messaging.exceptions.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class SpringContextServiceLookupImpl implements ServiceLookup {

	private final ApplicationContext applicationContext;
	private final Logger mainLogger = LoggerFactory.getLogger(SpringContextServiceLookupImpl.class);
	private final Logger errorLogger = LoggerFactory.getLogger("error");

	public SpringContextServiceLookupImpl(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public <T> T lookup(ServiceLookupRequest<T> request) {
		mainLogger.debug("Looking up service type ={}", request.getServiceType());
		try {
			return applicationContext.getBean(request.getServiceType());
		} catch (final NoSuchBeanDefinitionException ex) {
			errorLogger.error("No bean found for type = {}, reason = {}", request.getServiceType(),
					ExceptionUtil.generateStackTrace(ex));
			if (request.isCreateIfNotFound()) {
				return attemptToCreateNewService(request.getServiceType());
			}
			mainLogger.debug("Auto service creation not requested for {}. Throwing exception", request.getServiceType());
			throw new ConfigurationException("No handler found for type " + request.getServiceType()
					+ ". Auto service creation was not requested");
		}
	}

	private <T> T attemptToCreateNewService(Class<T> serviceType) {
		try {
			mainLogger.debug("Auto service creation requested for type = {}, Attempting to create",
					serviceType);
			final T instance = serviceType.getConstructor().newInstance();
			applicationContext.getAutowireCapableBeanFactory().initializeBean(instance, serviceType.getName());
			return instance;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException ex) {
			errorLogger.error("Failed to auto create service {} due to {}", serviceType, ExceptionUtil.generateStackTrace(ex));
			throw new SystemException("Failed to auto create requested service " + serviceType, ex);
		}
	}
}
