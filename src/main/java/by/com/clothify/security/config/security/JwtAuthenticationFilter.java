package by.com.clothify.security.config.security;

import by.com.clothify.security.model.dto.enums.AuthError;
import by.com.clothify.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
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

import static by.com.clothify.security.service.AuthenticateService.sendError;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {

        try {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                try {
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    log.error(AuthError.UNAUTHORIZED.getDescription());
                    sendError(response, HttpStatus.UNAUTHORIZED, AuthError.UNAUTHORIZED);
                }
                return;
            }

            final String jwt = authHeader.substring(7);
            final String userEmail;

            try {
                userEmail = jwtService.extractUsername(jwt);
            } catch (SignatureException se) {
                log.error(se.getMessage(), se);
                log.error(AuthError.INVALID_TOKEN.getDescription());
                sendError(response, HttpStatus.UNAUTHORIZED, AuthError.INVALID_TOKEN);
                return;
            } catch (ExpiredJwtException eje) {
                sendError(response, HttpStatus.UNAUTHORIZED, AuthError.TOKEN_EXPIRED);
                return;
            }

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error(AuthError.AUTH_ERROR.getDescription());
            sendError(response, HttpStatus.UNAUTHORIZED, AuthError.AUTH_ERROR);
        }
    }
}
