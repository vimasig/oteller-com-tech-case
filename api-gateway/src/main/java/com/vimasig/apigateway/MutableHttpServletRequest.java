package com.vimasig.apigateway;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders = new HashMap<>();

    public MutableHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public void putHeader(String name, String value) {
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> headerNames = new HashSet<>(customHeaders.keySet());
        Enumeration<String> originalHeaderNames = super.getHeaderNames();
        while (originalHeaderNames.hasMoreElements()) {
            headerNames.add(originalHeaderNames.nextElement());
        }
        return Collections.enumeration(headerNames);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (customHeaders.containsKey(name)) {
            return Collections.enumeration(Collections.singletonList(customHeaders.get(name)));
        }
        return super.getHeaders(name);
    }
}

