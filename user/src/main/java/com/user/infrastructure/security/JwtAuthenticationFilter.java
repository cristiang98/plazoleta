package com.user.infrastructure.security;

import com.user.domain.spi.IJwtHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtHandler jwtHandler;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            if (request.getServletPath().equals("/api/v1/user/login")) {
                // No procesar el token JWT para el login
                filterChain.doFilter(request, response);
                return;
            }

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

            // Continúa con la lógica original: extraer datos del token y autenticar al usuario
            final String userEmail = jwtHandler.extractUsername(jwtToken);
            final int dni = jwtHandler.extractDni(jwtToken);
            List<?> rawAuthorities = jwtHandler.extractClaim(jwtToken, claims -> claims.get("authorities", List.class));

// Convertir LinkedHashMap a una lista de strings (los roles directamente)
            List<String> roles = rawAuthorities.stream()
                    .map(authority -> (String) ((LinkedHashMap<?, ?>) authority).get("authority"))
                    .toList();

// Si solo necesitas un rol específico
            String role = roles.get(0);
            // Validar que el username esté presente y que el contexto de autenticación esté vacío
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Validar el token con los detalles del usuario (comprobaciones de validez como expiración, signatura, etc.)
                if (jwtHandler.isTokenValid(jwtToken, userDetails)) {
                    // Crea el objeto de autenticación para configurar el contexto de seguridad
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    dni,  //mandar el userdeatils
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(role)) // mandar el getAuthorities
                            );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Establece la autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            // Continúa con la cadena de filtros después de procesar el token
            filterChain.doFilter(request, response);
        } finally {
            // Limpia el contexto al final de cada petición
            SecurityContextHolder.clearContext();
        }
    }
}
