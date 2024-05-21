package com.tawandr.utils.rest.endpoint;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Java program to validate an url in Java
@Slf4j
class UrlUtils
{
	private static final String URL_REGEX =
			"^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
			"(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
			"([).!';/?:,][[:blank:]])?$";

	private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

	public static boolean validateUrl(String url) {
		// Validate an url
		final boolean isValidUrl = isValidUrl(url);
		if (isValidUrl) {
			log.info("The URL [{}] is valid URL", url);
		}
		else {
			log.error("The URL [{}] is NOT valid URL", url);
		}
		return isValidUrl;
	}
	public static boolean isValidUrl(String url) {

		if (url == null) {
			return false;
		}

		Matcher matcher = URL_PATTERN.matcher(url);
		return matcher.matches();
	}

	public static void main(String[] args)
	{
		validateUrl("https://www.techiedelight.com/");
		validateUrl("http://www.techiedelight.com");
		validateUrl("localhost:8080");

	}
}