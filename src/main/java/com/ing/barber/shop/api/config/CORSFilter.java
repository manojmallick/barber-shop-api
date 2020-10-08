package com.ing.barber.shop.api.config;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

  public static final String HEADER_ORIGIN = "Origin";
  public static final String HEADER_ALLOW_ORIGINS = "Access-Control-Allow-Origin";
  public static final String HEADER_ALLOWED_METHODS = "Access-Control-Allow-Methods";
  public static final String HEADER_ALLOWED_HEADERS = "Access-Control-Allow-Headers";
  public static final String HEADER_REQUEST_HEADERS = "Access-Control-Request-Headers";
  public static final String HEADER_VALUE_ALLOWED_METHODS =
      "GET, POST, OPTIONS, DELETE, PUT, HEAD, PATCH";
  public static final String DOMAIN_VALUE = ".*localhost";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if (null != httpRequest.getHeader(HEADER_ORIGIN)) {
      Pattern subDomainPattern = Pattern.compile(DOMAIN_VALUE);
      if (subDomainPattern.matcher(httpRequest.getHeader(HEADER_ORIGIN)).matches()) {
        httpResponse.addHeader(HEADER_ALLOW_ORIGINS, httpRequest.getHeader(HEADER_ORIGIN));
        httpResponse.addHeader(HEADER_ALLOWED_METHODS, HEADER_VALUE_ALLOWED_METHODS);
        httpResponse.addHeader(
            HEADER_ALLOWED_HEADERS, httpRequest.getHeader(HEADER_REQUEST_HEADERS));
      } else {
        httpResponse.addHeader(HEADER_ALLOW_ORIGINS, httpRequest.getHeader(HEADER_ORIGIN));
        httpResponse.addHeader(HEADER_ALLOWED_METHODS, HEADER_VALUE_ALLOWED_METHODS);
        httpResponse.addHeader(
            HEADER_ALLOWED_HEADERS, httpRequest.getHeader(HEADER_REQUEST_HEADERS));
      }

      if (HttpMethod.OPTIONS.matches(httpRequest.getMethod())) {
        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        return;
      }
    }

    chain.doFilter(httpRequest, httpResponse);
  }
}
