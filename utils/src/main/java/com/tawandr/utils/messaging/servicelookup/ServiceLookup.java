package com.tawandr.utils.messaging.servicelookup;

public interface ServiceLookup {

	<T> T lookup(ServiceLookupRequest<T> request);
	
}
