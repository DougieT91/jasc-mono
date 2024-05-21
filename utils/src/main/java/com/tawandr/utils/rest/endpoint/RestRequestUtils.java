package com.tawandr.utils.rest.endpoint;

import com.tawandr.utils.messaging.dtos.ApiRequestMetaData;
import com.tawandr.utils.messaging.dtos.StandardResponse;
import com.tawandr.utils.messaging.exceptions.ClientIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.*;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Base64;


import static com.tawandr.utils.object.validation.ElvisOperator.elvisOf;


/**
 * Created By Dougie T Muringani : 17/2/2020
 */

@Slf4j
public class RestRequestUtils {

    private RestRequestUtils() {
        super();
    }

    public static <S> ResponseEntity<StandardResponse<S>> createEntity(StandardResponse<S> standardResponse) {
        return createEntity(standardResponse, standardResponse.getResponseCode());
    }

    public static <T> ResponseEntity<T> createEntity(T body,
                                                     int status) {
        HttpStatus resolvedStatus = HttpStatus.resolve(status);

        final ResponseEntity<T> responseEntity;
        if (resolvedStatus != null) {
            responseEntity = new ResponseEntity<>(body, null, resolvedStatus);
        } else {
            responseEntity = ResponseEntity.status(status).body(body);
        }

        return responseEntity;
    }

    public static <T> HttpEntity<List<T>> getListHttpEntity(StandardResponse<T> standardResponse) {
        final List<T> result = standardResponse.getResult();
        return RestRequestUtils.createEntity(result, standardResponse.getResponseCode());
    }

    public static <T> ResponseEntity<List<T>> createEntity(List<T> body,
                                                           int status) {
        HttpStatus resolvedStatus = HttpStatus.resolve(status);

        final ResponseEntity<List<T>> responseEntity;
        if (resolvedStatus != null) {
            responseEntity = new ResponseEntity<>(body, null, resolvedStatus);
        } else {
            responseEntity = ResponseEntity.status(status).body(body);
        }

        return responseEntity;
    }

    public static void processApiRequestMetaData(final ApiRequestMetaData apiRequestMetaData,
                                                 final BaseApiConfigurationProperties apiProperties) {
        setDefaults(apiRequestMetaData, apiProperties);
    }

    public static HttpHeaders processClientHeaders(final BaseApiConfigurationProperties apiProperties) {
        return processClientHeaders(new HttpHeaders(), apiProperties);
    }

    public static HttpHeaders processClientHeaders(final HttpHeaders httpHeaders,
                                                   final BaseApiConfigurationProperties apiProperties) {
        final HttpHeaders headers = elvisOf(httpHeaders, new HttpHeaders());
        setHeaderDefaults(headers, apiProperties);
        setSecurityHeaders(headers, apiProperties);
        return headers;
    }

    private static void setSecurityHeaders(HttpHeaders httpHeaders,
                                           BaseApiConfigurationProperties apiProperties) {

        final String plainCredentials = apiProperties.getUserName() + ":" + apiProperties.getSecretKey();
        final byte[] plainCredentialsBytes = plainCredentials.getBytes();
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] base64CredentialsBytes = encoder.encode(plainCredentialsBytes);
        final String base64Credentials = new String(base64CredentialsBytes);

        httpHeaders.add("Authorization", "Basic " + base64Credentials);
    }

    private static void setHeaderDefaults(final HttpHeaders httpHeaders,
                                          final BaseApiConfigurationProperties properties) {
        log.debug("Setting Defaults for missing (Required) headers in Outgoing Rest Request");

        final String language = elvisOf(httpHeaders.getFirst("LANGUAGE"), properties.getLanguage());
        final String sourceName = elvisOf(httpHeaders.getFirst("SOURCE_NAME"), properties.getLanguage());
        final String sourceType = elvisOf(httpHeaders.getFirst("SOURCE_TYPE"), properties.getLanguage());

        httpHeaders.add("LANGUAGE", language);
        httpHeaders.add("SOURCE_NAME", sourceName);
        httpHeaders.add("SOURCE_TYPE", sourceType);

        httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("Set Headers::\n{}", httpHeaders);
    }

    private static void setDefaults(final ApiRequestMetaData metaData,
                                    final BaseApiConfigurationProperties properties) {
        log.trace("Setting Defaults for missing values in ApiRequestMetaData");
        metaData.setLanguage(elvisOf(metaData.getLanguage(), properties.getLanguage()));
        metaData.setSourceName(elvisOf(metaData.getSourceName(), properties.getSourceName()));
        metaData.setSourceType(elvisOf(metaData.getSourceType(), properties.getSourceType()));
        final String metaDataUserName = elvisOf(metaData.getPrincipal(), Principal::getName);
        metaData.setUserName(elvisOf(metaDataUserName, properties.getUserName()));
    }

    public static String joinUrl(final String baseUrlString,
                                 final String requestMapping,
                                 String fileString) {
        log.debug("Creating URL by Joining components:\n\tbaseUrlString: {},\n\trequestMapping: {},\n\tfileString: {}"
                , baseUrlString, requestMapping, fileString);
        if (baseUrlString == null) {
            throw new ClientIntegrationException("Join Url Failed: Base Url is required but not provided");
        }
        final String file = elvisOf(fileString);
        final URL baseUrl = processUrlProtocol(baseUrlString);
        return joinUrl(baseUrl, requestMapping, file);
    }

    private static URL processUrlProtocol(String baseUrlString) {
        URL baseUrl;
        final boolean hasValidUrlProtocol = hasValidUrlProtocol(baseUrlString);
        if (!hasValidUrlProtocol) {
            final String url = setDefaultUrlProtocol(baseUrlString);
            baseUrl = createURL(url);
        } else {
            baseUrl = createURL(baseUrlString);
        }
        return baseUrl;
    }

    private static URL createURL(URL url, int port) {
        try {
            return new URL(
                    url.getProtocol(),
                    url.getHost(),
                    port,
                    url.getFile(),
                    null);
        } catch (MalformedURLException e) {
            String errorMessage = String.format("Malformed URL Provided: %s", url);
            log.error(errorMessage);
            throw new ClientIntegrationException(errorMessage, e);
        }
    }

    public static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            String errorMessage = String.format("Malformed URL Provided: %s", url);
            log.error(errorMessage);
            throw new ClientIntegrationException(errorMessage, e);
        }
    }

    private static String joinUrl(URL baseUrl, final String... additionalUrlComponents) {
        final List<String> additionalUrlComponentsList = Arrays.asList(additionalUrlComponents);

        StringBuilder urlExtensionBuilder = new StringBuilder();
        urlExtensionBuilder.append(baseUrl.toString());
        additionalUrlComponentsList.forEach(urlPart -> {
                if(StringUtils.hasText(urlPart)){
                    urlExtensionBuilder.append("/").append(urlPart);
                }
        });
        return createURL(urlExtensionBuilder.toString()).toString();
    }

    private static String setDefaultUrlProtocol(String url) {
        log.debug("Adding default protocol to url string: {}", url);
        final String defaultProtocol = "http";
        final String protocolDelimiter = "://";

        final StringBuilder urlBuilder = new StringBuilder();
        if (url.contains(protocolDelimiter)) {
            final int endIndex = urlBuilder.indexOf(protocolDelimiter);
            urlBuilder.replace(0, endIndex, defaultProtocol);
        } else {
            urlBuilder.append(defaultProtocol).append(protocolDelimiter).append(url);
        }

        try {
            return createURL(urlBuilder.toString()).toString();
        } catch (Exception e) {
            final String errorMessage = String.format(
                    "failed to add default protocol to url String: %s", urlBuilder.toString());
            log.error(errorMessage);
            throw new ClientIntegrationException(errorMessage, e);
        }
    }

    private static boolean hasValidUrlProtocol(String string) {
        if (StringUtils.isEmpty(string)) {
            throw new ClientIntegrationException("Empty String Cannot be converted to string");
        }

        try {
            URL url = new URL(string);
            final String message = String.format("[%s] is a valid Url:%n%s", string, url.toString());
            log.debug(message);
            return true;
        } catch (MalformedURLException e) {
            if (e.toString().contains("unknown protocol")) {
                final String errorMessage = String.format("[%s] is NOT a valid Url. Missing or Unknown Protocol",
                        string);
                log.error(errorMessage);
            }
            return false;
        }

    }

    public static String processUrl(final String urlTemplate,
                                    final Map<String, String> nullableQueryParameters,
                                    final Map<String, String> nullablePathVariables
    ) {
        final URL url = processUrlProtocol(urlTemplate);
        final StringBuilder urlBuilder = new StringBuilder(url.toString());

        Map<String, String> queryParameters = elvisOf(nullableQueryParameters);
        Map<String, String> pathVariables = elvisOf(nullablePathVariables);

        pathVariables.forEach((key, value) -> substitutePathVariables(urlBuilder, key, value));
        queryParameters.forEach((key, value) -> addQueryParameters(urlBuilder, key, value));

        final String processedUrl = urlBuilder.toString();
        log.debug("Processed Url::\n\tREQUEST: \n\t\turl: {}\n\t\tQuery Parameters: {}\n\t\tPath Variables: {}" +
                "\n\tRESULT: \n\t\turl: {}", urlTemplate, queryParameters, pathVariables, processedUrl);
        return processedUrl;
    }

    private static void substitutePathVariables(final StringBuilder url,
                                                final String variable,
                                                final Object value) {
        final int index = url.indexOf(variable);
        final int start = index - 1;
        final int end = index + variable.length() + 1;
        url.replace(start, end, value.toString());
    }

    private static void addQueryParameters(final StringBuilder url,
                                           final String parameter,
                                           final Object value) {
        final String uriString = UriComponentsBuilder
                .fromUriString(url.toString())
                .replaceQueryParam(parameter, value)
                .toUriString();
        url.replace(0, url.length(), uriString);
    }

    public static String getAddressFromHostsFile(String hostName) {
        try {
            final InetAddress ip = InetAddress.getByName(hostName);
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            final String errorMessage = String.format("Failed to resolve host [%s].%nERROR:: %s",
                    hostName, e.toString());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    public static String addPortToUrl(final String urlString, final String port) {
        log.info("Adding [port: {}] to [URL: {}]", port, urlString);
        final URL url = processUrlProtocol(urlString);
        int portInt;
        try {
            portInt = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            final String errorMessage = String
                    .format("Failed to Convert provided Port [%s] to Int%nError:%n%s", port, e.getMessage());
            log.error("{}.\n Setting default port...", errorMessage);
            portInt = 0;
        }
        return createURL(url, portInt).toString();
    }

    public static URI createURI(String url) {
        try {
            return createURL(url).toURI();
        } catch (URISyntaxException e) {
            final String errorMessage = String.format("Failed to Create URI%nError:%n%s", e.getMessage());
            log.error(e.toString());
            throw new ClientIntegrationException(errorMessage, e);
        }
    }
}
