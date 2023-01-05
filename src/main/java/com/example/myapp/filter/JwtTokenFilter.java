package com.example.myapp.filter;

import com.example.myapp.entites.User;
import com.example.myapp.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    // If the Authorization header of the request doesn’t contain a Bearer token,
    // It continues the filter chain without updating authentication context.
    if(!hasAuthorizationBearer(request)){
      filterChain.doFilter(request, response);
      return;
    }

    // Else, if the token is not verified, continue the filter chain without updating authentication context.
    String token = getAccessToken(request);
    if(!jwtTokenUtil.validateAccessToken(token)){
      filterChain.doFilter(request, response);
      return;
    }

    //If the token is verified, update the authentication context with the user details ID and email.
    setAuthenticationContext(token, request);
    //In other words, it tells Spring that the user is authenticated, and continue the downstream filters.
    filterChain.doFilter(request, response);
  }

  private boolean hasAuthorizationBearer(HttpServletRequest request){
    String header = request.getHeader("Authorization");
    if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
      return false;
    }

    return true;
  }

  private String getAccessToken(HttpServletRequest request){
    String token = null;
    try{
      String header = request.getHeader("Authorization");
      token = header.split(" ")[1].trim();
    } catch (ArrayIndexOutOfBoundsException exception){
      log.error("Bearer is null", exception.getMessage());
    }

    return token;
  }

  private void setAuthenticationContext(String token, HttpServletRequest request){
    UserDetails userDetails = getUserDetails(token);
    UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }

  private UserDetails getUserDetails(String token){
    User userDetails = new User();

    String[] jwtSubject = jwtTokenUtil.getSubject(token).split(",");
    userDetails = (User) userDetailsService.loadUserByUsername(jwtSubject[1]);
    return userDetails;
  }

}

