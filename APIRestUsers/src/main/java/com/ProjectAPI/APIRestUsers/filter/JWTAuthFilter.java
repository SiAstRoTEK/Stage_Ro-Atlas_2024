package com.ProjectAPI.APIRestUsers.filter;

import com.ProjectAPI.APIRestUsers.service.impl.JwtService;
import com.ProjectAPI.APIRestUsers.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//extends perchè vogliamo che il filtro venga eseguito per ogni richiesta
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    //facciamo injection del JwtService e di UserDetailsImpl per usarne i metodi
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsImpl;

    public JWTAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsImpl) {
        this.jwtService = jwtService;
        this.userDetailsImpl = userDetailsImpl;
    }


    //necessita di 3 parametri, gli forziamo il decoratore @NonNull per forzare che non siano mai nulli
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        //creiamo l'header della request passandoci i parametri necessari
        String authHeader = request.getHeader("Authorization");

        //controllo che l'header contenga qualcosa e che sia del tipo giusto
        //se non passa il primo controllo va al prossimo filter
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        //estraiamo dall'header l'effettivo token togliendo l'intestazione "Bearer " 7char
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        //controlliamo che esista e non sia già autenticato
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsImpl.loadUserByUsername(username);

            if (jwtService.isValid(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
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
