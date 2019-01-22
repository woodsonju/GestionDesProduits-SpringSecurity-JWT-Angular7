package com.wj.service;

import com.wj.dao.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/*
      Si spring security veut savoir si un utilisateur existe ou pas
      il appelera la méthode loadUserByUsername
   */

@Service  //Cette Classe va être instancier au démarrage de l'application en tant que service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private AccountService accountService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = accountService.findUserByUsername(username);
        
        if(appUser == null) 
            throw new UsernameNotFoundException("Invalid user");

        //les rôles pour Spring Security sont des objets d'une Collection de type GrantedAuthority
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(r ->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        
        //On va retourner à Spring Security un objet de type User (un objet User de Spring)
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
   
    }
}
