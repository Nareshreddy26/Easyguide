package com.gova.EasyGuide.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//
//    @Override
//    public void addCorsMappings(CorsRegistry cors)
//    {
//        cors
//                .addMapping("/**")
//                .allowCredentials(true)
//                .allowedMethods("GET","POST","PUT","DELETE")
//                .maxAge(3600)
//                .allowedOrigins("http://localhost:5173")
//                .allowedHeaders("*");
//
//    }
//
//}








//@Override
//protected void doFilterInternal(HttpServletRequest request,
//                                HttpServletResponse response,
//                                FilterChain filterChain)
//        throws ServletException, IOException {
//
//    String accessToken = null;
//    String refreshToken = null;
//
//    // 1. Extract tokens from request
//    String authHeader = request.getHeader("Authorization");
//    if (authHeader != null && authHeader.startsWith("Bearer ")) {
//        accessToken = authHeader.substring(7);
//    } else if (request.getCookies() != null) {
//        for (Cookie cookie : request.getCookies()) {
//            if (cookie.getName().equals("JWT_TOKEN")) {
//                accessToken = cookie.getValue();
//            } else if (cookie.getName().equals("REFRESH_TOKEN")) {
//                refreshToken = cookie.getValue();
//            }
//        }
//    }
//
//    try {
//        if (accessToken != null) {
//            String username = jwtService.extractUserName(accessToken);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                if (!jwtService.validateToken(accessToken, userDetails)) {
//                    // Access token is invalid but not expired (wrong signature, etc)
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                    return;
//                }
//
//                // Create authentication
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//    } catch (ExpiredJwtException ex) {
//        // Access token expired - try to refresh
//        if (refreshToken != null) {
//            try {
//                String username = ex.getClaims().getSubject();
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                if (!jwtService.isTokenExpired(refreshToken)) {
//                    // Generate new access token
//                    String newAccessToken = jwtService.refreshAccessToken(refreshToken, userDetails);
//
//                    // Set new cookie
//                    ResponseCookie newCookie = ResponseCookie.from("JWT_TOKEN", newAccessToken)
//                            .httpOnly(true)
//                            .secure(false)
//                            .path("/")
//                            .maxAge(24 * 60 * 60)
//                            .sameSite("Lax")
//                            .build();
//                    response.addHeader("Set-Cookie", newCookie.toString());
//
//                    // Continue with the new token
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                } else {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired");
//                    return;
//                }
//            } catch (Exception e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token refresh failed");
//                return;
//            }
//        }
//        else {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
//            return;
//        }
//    }
//
//    filterChain.doFilter(request, response);
//}