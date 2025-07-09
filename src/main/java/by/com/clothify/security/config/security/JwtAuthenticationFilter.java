package by.com.clothify.security.config.security;

import by.com.clothify.security.model.dto.enums.AuthError;
import by.com.clothify.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static by.com.clothify.security.service.AuthService.sendError;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_HEADER = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {

        try {
            final String authHeader = request.getHeader(AUTH_HEADER);
            if (!authHeaderCheck(request, response, filterChain, authHeader)) {
                return;
            }

            final String jwt = authHeader.substring(BEARER_HEADER.length());

            Optional<String> optionalUserEmail = getOptionalUserEmail(response, jwt);
            if (optionalUserEmail.isEmpty()) {
                return;
            }
            final String userEmail = optionalUserEmail.get();

            setAuthentication(request, response, filterChain, userEmail, jwt);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error(AuthError.AUTH_ERROR.getDescription());
            sendError(response, HttpStatus.UNAUTHORIZED, AuthError.AUTH_ERROR);
        }
    }

    private boolean authHeaderCheck(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain, String authHeader) {
        if (authHeader == null || !authHeader.startsWith(BEARER_HEADER)) {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log.error(AuthError.UNAUTHORIZED.getDescription());
                sendError(response, HttpStatus.UNAUTHORIZED, AuthError.UNAUTHORIZED);
            }
            return false;
        }
        return true;
    }

    private Optional<String> getOptionalUserEmail(@NonNull HttpServletResponse response, String jwt) {
        try {
            return Optional.of(jwtService.extractUsername(jwt));

        } catch (SignatureException se) {
            log.error(se.getMessage(), se);
            log.error(AuthError.INVALID_TOKEN.getDescription());
            sendError(response, HttpStatus.UNAUTHORIZED, AuthError.INVALID_TOKEN);
            return Optional.empty();

        } catch (ExpiredJwtException eje) {
            sendError(response, HttpStatus.UNAUTHORIZED, AuthError.ACCESS_TOKEN_EXPIRED);
            return Optional.empty();
        }
    }

    private void setAuthentication(@NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain,
                                   String userEmail, String jwt) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
