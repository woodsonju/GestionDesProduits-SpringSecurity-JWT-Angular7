package com.wj.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wj.dao.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;
    
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        AppUser appUser = null;
        
        /*
            ObjectMapper permet de prendre des objets JSON et de les stocker dans un Objet Spring.
            request.getInputStream() est le contenu de la requête.
		  	AppUser.class : on le deserialise dans un objet de type AppUser.
         */
        try {
            appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
        } catch (IOException e) {
            new RuntimeException(e);
        }

        System.out.println("**********************");
        System.out.println("username:" + appUser.getUsername());
        System.out.println("password:" + appUser.getPassword());
        
        return authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        /*
            authResult va nous permettre de récuprer l'utilisateur qui s'est authentifié
            getPrincipal nous retourne le nom de l'utilisateur authentitié
            getAuthorities nous retourne les roles de l'utilisateur
         */
        User user = (User) authResult.getPrincipal();  
        
        //J'ajoute l'ensemble des roles qui se trouvent dans authResult dans ma liste roles
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(a-> {
            roles.add(a.getAuthority());
        });
        
        /*
            Creation du jwt
            withIssuer : le nom de l'autorité de l'application qui a généré le token
         */
        String jwt = JWT.create()
                .withIssuer(request.getRequestURI())
                .withSubject(user.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET));
        
        
        //Envoie  du jwt dans la réponse http
        response.addHeader(SecurityConstants.HEADER_NAME, jwt);
    }
    
}
