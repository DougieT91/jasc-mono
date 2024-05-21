package com.tawandr.utils.messaging.servicelookup;

public class ServiceLookupRequest<T> {

	private final Class<T> serviceType;
	private final boolean createIfNotFound;

	public ServiceLookupRequest(Class<T> serviceType, boolean createIfNotFound) {
		this.serviceType = serviceType;
		this.createIfNotFound = createIfNotFound;
	}

	public Class<T> getServiceType() {
		return serviceType;
	}

	public boolean isCreateIfNotFound() {
		return createIfNotFound;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (createIfNotFound ? 1231 : 1237);
		result = prime * result + ((serviceType == null) ? 0 : serviceType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceLookupRequest<T> other = (ServiceLookupRequest<T>) obj;
		if (createIfNotFound != other.createIfNotFound)
			return false;
		if (serviceType == null) {
			if (other.serviceType != null)
				return false;
		} else if (!serviceType.equals(other.serviceType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceLookupRequest [serviceType=" + serviceType + ", createIfNotFound=" + createIfNotFound + "]";
	}

	
	
}
