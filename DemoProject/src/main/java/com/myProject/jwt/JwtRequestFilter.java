package com.myProject.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.myProject.services.UserDetailsServiceImplementation;

public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImplementation userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			final String authorizationHeader = request.getHeader("Authorization");
			String username = null;
			String jwt = null;
			
			if(authorizationHeader!= null && authorizationHeader.startsWith("Bearer "))
			{
				jwt = authorizationHeader.substring(7);
				username = jwtUtils.getUserNameFromJwtToken(jwt);
			}
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if(jwtUtils.validateJwtToken(jwt))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
									userDetails,null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
	}
}
