package com.ead.notification.configs;

import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TraceFilter implements Filter {

    private static final String REQUEST_ID_HEADER_NAME = "request-id";
    private static final String APPLICATION_HEADER_NAME = "application-name";
    private final Tracer tracer;
    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        if (!response.getHeaderNames().contains(REQUEST_ID_HEADER_NAME)) {
            if (Optional.of(tracer).map(Tracer::currentTraceContext).map(CurrentTraceContext::context).isEmpty()) {
                chain.doFilter(req, res);
                return;
            }
//            var context = tracer.currentTraceContext().context();
//            var traceId = context.traceId();
//            response.setHeader(REQUEST_ID_HEADER_NAME, traceId);
            response.setHeader(APPLICATION_HEADER_NAME, appName);
        }
        chain.doFilter(req, res);
    }
}
