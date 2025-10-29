package com.cooksys.team1groupproject.services;

public interface ValidateService {

    // GET validate/tag/exists/{label}
    Boolean validateTag(String label);

    // GET validate/username/exists/@{username}
    Boolean validateUsername(String username);

    // GET validate/username/available/@{username}
    Boolean validateUsernameIsAvailable(String username);

}
