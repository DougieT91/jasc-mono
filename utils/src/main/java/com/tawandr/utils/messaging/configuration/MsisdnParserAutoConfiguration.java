package com.tawandr.utils.messaging.configuration;

import com.tawandr.utils.messaging.i18.api.MessageSourceService;
import com.tawandr.utils.messaging.i18.impl.MessageSourceServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
//@ConditionalOnClass({ MsisdnParser.class })
public class MsisdnParserAutoConfiguration {

//	@ConditionalOnMissingBean
//	@Bean
//	public MsisdnParser msisdnParser(MessageSourceService messageSourceService) {
//		return new EconetMsisdnParser(messageSourceService);
//	}

	@ConditionalOnMissingBean
	@Bean
	public MessageSourceService messageSourceService() {
		return new MessageSourceServiceImpl(messageSource());
	}

	private MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
