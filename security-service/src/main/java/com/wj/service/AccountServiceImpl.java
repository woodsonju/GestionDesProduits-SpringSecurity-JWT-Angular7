package com.wj.service;

import com.wj.dao.AppRoleRepository;
import com.wj.dao.AppUserRepository;
import com.wj.dao.entities.AppRole;
import com.wj.dao.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
   
    /*
        On peut faire l'injection de dépendance  deux façon 
        avec Autowired ou via un constructeur
        Il utilisera le constructeur pour instancier cette classe au démarrage 
        de l'application
     */

    /*
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }
    */
    
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) { 
        
        AppUser user = appUserRepository.findByUsername(username);

        //On verifie si l'utilisateur existe déjà dans la base de données
        if(user != null) 
            throw new RuntimeException("This user already exists");
        
        if(!password.equals(confirmedPassword)) 
            throw new RuntimeException("Please confirm your password");
        
        //Si l'utilisateur n'existe pas on va le créer
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setActived(true); //On suppose qu'un utilisateur qui vient d'être ajouté est activé
        // On encode  en bCrypt, le mot de passe saisie par l'utilisateur avant de l'ajouter à la base de données.
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        //On l'ajoute dans la base de données
        appUserRepository.save(appUser); 
        //Une fois l'utilisateur enregistré, on lui donne un rôle par défaut
        this.addRoleToUser(username, "USER");
        return appUser;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return appRoleRepository.save(role) ;
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);
    }
}
