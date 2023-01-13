package com.myusufalpian.projectecommerce.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter{
    @Value("${access-control.allow-origin:*}")
	private String allowOrigin;
	
	@Value("${access-control.allow-methods:GET, POST, PUT, OPTIONS, DELETE}")
	private String allowMethods;
	
	@Value("${access-control.allow-headers:x-requested-with, Authorization, Accept, Content-Type, token, clicked, email}")
	private String allowHeaders;
					
	@Value("${access-control.max-age:86000}")
    private String maxAge;

    @Value("${access-control.x-frame-options:DENY}")
    private String xFrameOptions;
			
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

		response.setHeader("Access-Control-Allow-Origin", allowOrigin);
		response.setHeader("Access-Control-Allow-Methods", allowMethods);
		response.setHeader("Access-Control-Max-Age", maxAge);
        response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        response.setHeader("X-Frame-Options", xFrameOptions);

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {

			response.setStatus(HttpServletResponse.SC_OK);

		} else {
			
			chain.doFilter(req, res);
			
		}

	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

}
