package com.talentica.esi.filter;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;

import com.talentica.esi.config.EsiConfig;
import com.talentica.esi.config.EsiServerResources;

public class EsiFilter implements Filter{
	private EsiServerResources esiServerResources;

	public void init(FilterConfig filterConfig) throws ServletException {
		String filePath = filterConfig.getInitParameter("configFilePath");
		try {
			this.esiServerResources = new EsiServerResources(filePath);
		} catch (IOException | ParseException e) {
			throw new ServletException("could not load esi server resources", e);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();
		EsiConfig ec = this.esiServerResources.getEcConfig();
		ESIHandler esiHandler = new ESIHandler(ec.getPageLetConfig(requestURI), 
												this.esiServerResources,
												response);
		try {
			esiHandler.handle();
		} catch (InterruptedException | ExecutionException e) {
			throw new ServletException("failed to handle esi request", e);
		}
	}
	public void destroy() {}
}