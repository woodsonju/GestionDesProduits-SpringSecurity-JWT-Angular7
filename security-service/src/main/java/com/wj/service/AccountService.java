package com.wj.service;

import com.wj.dao.entities.AppRole;
import com.wj.dao.entities.AppUser;

/*
 * On va créer une une interface dans la couche service
 * dans laquelle je vais centraliser la gestion des utilisateur et les
 * rôle. On va gérer les comptes des utilisateurs.
 */

public interface AccountService {
    
    //confirmedPassword : Quand un un utilisateur s'authentifie il faut qu'il confirme son mot de passe
    public AppUser saveUser(String username, String password, String confirmedPassword);
    
    public AppRole saveRole(AppRole role);
    
    public AppUser findUserByUsername(String username);
    
    public void addRoleToUser(String username, String roleName);
    
}
