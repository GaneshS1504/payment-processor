package com.ratelimiter.token_bucket;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ratelimiter.token_bucket.service.RateLimiterService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestFilter extends OncePerRequestFilter {

	private RateLimiterService rateLimiterService;

	public RequestFilter(RateLimiterService rateLimiterService) {
		super();
		this.rateLimiterService = rateLimiterService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getRequestURI().startsWith("/api/v1")) {
			String id = request.getHeader("x-tenant-id");
			if (io.micrometer.common.util.StringUtils.isNotBlank(id)) {
				Bucket bucket = rateLimiterService.resolveBucket(Integer.parseInt(id));
				ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
				if (probe.isConsumed()) {
					request.setAttribute("tooManyRequests", true);
					response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
				} else {
					long waitTime = probe.getNanosToWaitForRefill() / 1_000_000_000;
					request.setAttribute("tooManyRequests", false);
				}
				filterChain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).setStatus(403);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

}
