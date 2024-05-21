package com.tawandr.utils.messaging.enums;

/**
 * Created By Dougie T Muringani : 18/7/2019
 */
public enum HttpResponseCodeDescription{

    _100 ("CONTINUE: This interim response indicates that everything so far is OK and that the client should continue with the request or ignore it if it is already finished."),
    _101 ("SWITCHING_PROTOCOL: This code is sent in response to an Upgrade request header by the client, and indicates the protocol the server is switching to."),
    _102 ("PROCESSING(WEBDAV): This code indicates that the server has received and is processing the request, but no response is available yet."),
    _103 ("EARLY_HINTS: This status code is primarily intended to be used with the Link header to allow the user agent to start preloading resources while the server is still preparing a response."),
    _200 ("OK: The request has succeeded. The meaning of a success varies depending on the HTTP method (GET, HEAD, PUT, POST, TRACE)."),
    _201 ("CREATED: The request has succeeded and a new resource has been created as a result of it. This is typically the response sent after a POST request, or after some PUT requests."),
    _202 ("ACCEPTED: The request has been received but not yet acted upon. It is non-committal, meaning that there is no way in HTTP to later send an asynchronous response indicating the outcome of processing the request. It is intended for cases where another process or server handles the request, or for batch processing."),
    _203 ("NON_AUTHORITATIVE_INFORMATION: This response code means returned meta-information set is not exact set as available from the origin server, but collected from a local or a third party copy. Except this condition, 200 OK response should be preferred instead of this response."),
    _204 ("NO_CONTENT: There is no content to send for this request, but the headers may be useful. The user-agent may update its cached headers for this resource with the new ones."),
    _205 ("RESET_CONTENT: This response code is sent after accomplishing request to tell user agent reset document view which sent this request."),
    _206 ("PARTIAL_CONTENT: This response code is used because of range header sent by the client to separate download into multiple streams."),
    _207 ("MULTI_STATUS(WEBDAV): A Multi-Status response conveys information about multiple resources in situations where multiple status codes might be appropriate."),
    _208 ("MULTI_STATUS(WEBDAV): Used inside a DAV: propstat response element to avoid enumerating the internal members of multiple bindings to the same collection repeatedly."),
    _226 ("IM_USED(HTTP_DELTAENCODING): The server has fulfilled a GET request for the resource, and the response is a representation of the result of one or more instance-manipulations applied to the current instance."),
    _300 ("MULTIPLE_CHOICE: The request has more than one possible response. The user-agent or user should choose one of them. There is no standardized way of choosing one of the responses."),
    _301 ("MOVED_PERMANENTLY: This response code means that the URI of the requested resource has been changed permanently. Probably, the new URI would be given in the response."),
    _302 ("FOUND: This response code means that the URI of requested resource has been changed temporarily. New changes in the URI might be made in the future. Therefore, this same URI should be used by the client in future requests."),
    _303 ("SEE_OTHER: The server sent this response to direct the client to get the requested resource at another URI with a GET request."),
    _304 ("NOT_MODIFIED: This is used for caching purposes. It tells the client that the response has not been modified, so the client can continue to use the same cached version of the response."),
    _305 ("USE_PROXY: Was defined in a previous version of the HTTP specification to indicate that a requested response must be accessed by a proxy. It has been deprecated due to security concerns regarding in-band configuration of a proxy."),
    _306 ("UNUSED: This response code is no longer used, it is just reserved currently. It was used in a previous version of the HTTP 1.1 specification."),
    _307 ("TEMPORARY_REDIRECT: The server sends this response to direct the client to get the requested resource at another URI with same method that was used in the prior request. This has the same semantics as the 302 Found HTTP response code, with the exception that the user agent must not change the HTTP method used: If a POST was used in the first request, a POST must be used in the second request."),
    _308 ("PERMANENT_REDIRECT: This means that the resource is now permanently located at another URI, specified by the Location: HTTP Response header. This has the same semantics as the 301 Moved Permanently HTTP response code, with the exception that the user agent must not change the HTTP method used: If a POST was used in the first request, a POST must be used in the second request."),
    _400 ("BAD_REQUEST: This response means that server could not understand the request due to invalid syntax."),
    _401 ("UNAUTHORIZED: Although the HTTP standard specifies \"unauthorized\", semantically this response means \"unauthenticated\". That is, the client must authenticate itself to get the requested response."),
    _402 ("PAYMENT_REQUIRED: This response code is reserved for future use. Initial aim for creating this code was using it for digital payment systems, however this status code is used very rarely and no standard convention exists."),
    _403 ("FORBIDDEN: The client does not have access rights to the content, i.e. they are unauthorized, so server is rejecting to give proper response. Unlike 401, the client's identity is known to the server."),
    _404 ("NOT_FOUND: The server can not find requested resource. In the browser, this means the URL is not recognized. In an API, this can also mean that the endpoint is valid but the resource itself does not exist. Servers may also send this response instead of 403 to hide the existence of a resource from an unauthorized client. This response code is probably the most famous one due to its frequent occurence on the web."),
    _405 ("METHOD_NOT_ALLOWED: The request method is known by the server but has been disabled and cannot be used. For example, an API may forbid DELETE-ing a resource. The two mandatory methods, GET and HEAD, must never be disabled and should not return this error code."),
    _406 ("NOT_ACCEPTABLE: This response is sent when the web server, after performing server-driven content negotiation, doesn't find any content following the criteria given by the user agent."),
    _407 ("PROXY_AUTHENTICATION_REQUIRED: This is similar to 401 but authentication is needed to be done by a proxy."),
    _408 ("REQUEST_TIMEOUT: This response is sent on an idle connection by some servers, even without any previous request by the client. It means that the server would like to shut down this unused connection. This response is used much more since some browsers, like Chrome, Firefox 27+, or IE9, use HTTP pre-connection mechanisms to speed up surfing. Also note that some servers merely shut down the connection without sending this message."),
    _409 ("CONFLICT: This response is sent when a request conflicts with the current state of the server."),
    _410 ("GONE: This response would be sent when the requested content has been permanently deleted from server, with no forwarding address. Clients are expected to remove their caches and links to the resource. The HTTP specification intends this status code to be used for \"limited-time, promotional services\". APIs should not feel compelled to indicate resources that have been deleted with this status code."),
    _411 ("LENGTH_REQUIRED: Server rejected the request because the Content-Length header field is not defined and the server requires it."),
    _412 ("PRECONDITION_FAILED: The client has indicated preconditions in its headers which the server does not meet."),
    _413 ("PAYLOAD_TOO_LARGE: Request entity is larger than limits defined by server; the server might close the connection or return an Retry-After header field."),
    _414 ("URI_TOO_LONG: The URI requested by the client is longer than the server is willing to interpret."),
    _415 ("UNSUPPORTED_MEDIA_TYPE: The media format of the requested data is not supported by the server, so the server is rejecting the request."),
    _416 ("REQUESTED_RANGE_NOT_SATISFIABLE: The range specified by the Range header field in the request can't be fulfilled; it's possible that the range is outside the size of the target URI's data."),
    _417 ("EXPECTATION_FAILED: This response code means the expectation indicated by the Expect request header field can't be met by the server."),
    _418 ("I'M_A_TEAPOT: The server refuses the attempt to brew coffee with a teapot."),
    _421 ("MISDIRECTED_REQUEST: The request was directed at a server that is not able to produce a response. This can be sent by a server that is not configured to produce responses for the combination of scheme and authority that are included in the request URI."),
    _422 ("UNPROCESSABLE_ENTITY(WEBDAV): The request was well-formed but was unable to be followed due to semantic errors."),
    _423 ("LOCKED(WEBDAV): The resource that is being accessed is locked."),
    _424 ("FAILED_DEPENDENCY(WEBDAV): The request failed due to failure of a previous request."),
    _425 ("TOO_EARLY: Indicates that the server is unwilling to risk processing a request that might be replayed."),
    _426 ("UPGRADE_REQUIRED: The server refuses to perform the request using the current protocol but might be willing to do so after the client upgrades to a different protocol. The server sends an Upgrade header in a 426 response to indicate the required protocol(s)."),
    _428 ("PRECONDITION_REQUIRED: The origin server requires the request to be conditional. Intended to prevent the 'lost update' problem, where a client GETs a resource's state, modifies it, and PUTs it back to the server, when meanwhile a third party has modified the state on the server, leading to a conflict."),
    _429 ("TOO_MANY_REQUESTS: The user has sent too many requests in a given amount of time (\"rate limiting\")."),
    _431 ("REQUEST_HEADER_FIELDS_TOO_LARGE: The server is unwilling to process the request because its header fields are too large. The request MAY be resubmitted after reducing the size of the request header fields."),
    _451 ("UNAVAILABLE_FOR_LEGAL_REASONS: The user requests an illegal resource, such as a web page censored by a government."),
    _500 ("INTERNAL_SERVER_ERROR: The server has encountered a situation it doesn't know how to handle."),
    _501 ("NOT_IMPLEMENTED: The request method is not supported by the server and cannot be handled. The only methods that servers are required to support (and therefore that must not return this code) are GET and HEAD."),
    _502 ("BAD_GATEWAY: This error response means that the server, while working as a gateway to get a response needed to handle the request, got an invalid response."),
    _503 ("SERVICE_UNAVAILABLE: The server is not ready to handle the request. Common causes are a server that is down for maintenance or that is overloaded. Note that together with this response, a user-friendly page explaining the problem should be sent. This responses should be used for temporary conditions and the Retry-After: HTTP header should, if possible, contain the estimated time before the recovery of the service. The webmaster must also take care about the caching-related headers that are sent along with this response, as these temporary condition responses should usually not be cached."),
    _504 ("GATEWAY_TIMEOUT: This error response is given when the server is acting as a gateway and cannot get a response in time."),
    _505 ("HTTP_VERSION_NOT_SUPPORTED: The HTTP version used in the request is not supported by the server."),
    _506 ("VARIANT_ALSO_NEGOTIATES: The server has an internal configuration error: the chosen variant resource is configured to engage in transparent content negotiation itself, and is therefore not a proper end point in the negotiation process."),
    _507 ("INSUFFICIENT_STORAGE(WEBDAV): The method could not be performed on the resource because the server is unable to store the representation needed to successfully complete the request."),
    _508 ("LOOP_DETECTED(WEBDAV): The server detected an infinite loop while processing the request."),
    _510 ("NOT_EXTENDED: Further extensions to the request are required for the server to fulfill it."),
    _511 ("NETWORK_AUTHENTICATION_REQUIRED: The 511 status code indicates that the client needs to authenticate to gain network access.");


    private String description;

    private HttpResponseCodeDescription(String code) {
        this.description = code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDescription(int code) {
        return getDescription();
    }

}
