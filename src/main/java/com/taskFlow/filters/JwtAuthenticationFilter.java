package com.taskFlow.filters;

import com.taskFlow.exceptions.JwtTokenException;
import com.taskFlow.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService
      userDetailsService; // this is inbuilt spring class for user verification
  private final JwtTokenException jwtTokenException;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      final String authHeader = request.getHeader("Authorization");
      if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) {

        String token = authHeader.substring(7);
        String loginId = jwtUtil.extractUserName(token);

        if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);

          if (jwtUtil.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }
        }
      }
      filterChain.doFilter(request, response);

    } catch (ExpiredJwtException e) {
      jwtTokenException.handle(response, 401, "jwt token expired");
    } catch (JwtException | IllegalArgumentException e) {
      jwtTokenException.handle(response, 401, "Invalid jwt token");
    }
  }
}
