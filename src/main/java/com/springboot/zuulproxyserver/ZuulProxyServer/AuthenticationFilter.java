package com.springboot.zuulproxyserver.ZuulProxyServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AuthenticationFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		System.out.println("inside shouldFilter");

		HttpServletRequest httpRequest = RequestContext.getCurrentContext().getRequest();
		// String servletPath = httpRequest.getServletPath();
		String method = httpRequest.getMethod();
		System.out.println("url method: " + method);
		return HttpMethod.GET.matches(method);
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String authorization = request.getHeader("Authorization");
		if (null == authorization || !authorization.startsWith("Bearer ")) {
			throw new ZuulException("", HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized");
		}

		System.out.println("inside run!!!!!!!!!!!!!!!!!!" + request.getRequestURL().toString());
		return null;
	}

	@Override
	public String filterType() {
		System.out.println("inside filterType");
		return "pre";
	}

	@Override
	public int filterOrder() {
		System.out.println("inside filterOrder");
		return 1;
	}

}
