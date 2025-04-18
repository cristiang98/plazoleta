package com.plaza.infrastructure.security;

import com.plaza.domain.spi.IJwtHanlderPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtHanlderPort jwtHandler;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

// Obtiene el token desde `Authorization` o `Cookie`
        String jwtToken = null;
        final String authHeader = request.getHeader("Authorization");

        // Verifica si el encabezado Authorization está presente y contiene "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // Extrae el token quitando "Bearer "
        } else {
            // Si no hay Authorization, intenta obtener el token desde las cookies
            String cookies = request.getHeader("Cookie");
            if (cookies != null && cookies.contains("token=")) {
                // Busca específicamente la cookie "token"
                for (String cookie : cookies.split(";")) {
                    cookie = cookie.trim(); // Quita espacios en blanco
                    if (cookie.startsWith("token=")) {
                        jwtToken = cookie.substring(6); // Extrae el valor del token
                        break;
                    }
                }
            }
        }

        // Si no hay token, continúa la cadena de filtros
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }


        // Extrae datos del token
        final String userEmail = jwtHandler.extractUsername(jwtToken); // Obtiene el correo (opcional)
        final Integer dni = jwtHandler.extractDni(jwtToken);


        // Manejo de roles como lista (forma generalizada)
        List<?> rawAuthorities = jwtHandler.extractClaim(jwtToken, claims -> claims.get("authorities", List.class));
        List<String> roles = rawAuthorities.stream()
                .map(authority -> (String) ((LinkedHashMap<?, ?>) authority).get("authority"))
                .toList();

        // Valida que haya al menos un rol y selecciona el primero como principal
        if (roles.isEmpty()) {
            log.warn("El token no contiene roles válidos");
            filterChain.doFilter(request, response);
            return;
        }

        String role = roles.get(0);

        // Valida que el username esté presente y que el contexto de autenticación esté vacío
        if (dni != 0 && SecurityContextHolder.getContext().getAuthentication() == null && jwtHandler.isTokenValid(jwtToken)) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            dni,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority(role))
                    );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("El usuario con DNI {} ha sido autenticado con el rol {}", dni, role);
        }


        // Continúa con la cadena de filtros después de procesar el token
        filterChain.doFilter(request, response);
    }
}
