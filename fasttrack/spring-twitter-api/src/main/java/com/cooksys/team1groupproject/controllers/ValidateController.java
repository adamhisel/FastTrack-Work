package com.cooksys.team1groupproject.controllers;


import com.cooksys.team1groupproject.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public Boolean validateTag(@PathVariable String label) {
        return validateService.validateTag(label);
    }

    @GetMapping("/username/exists/@{username}")
    public Boolean validateUsername(@PathVariable String username) {
        return validateService.validateUsername(username);
    }

    @GetMapping("/username/available/@{username}")
    public Boolean validateUsernameIsAvailable(@PathVariable String username) {
        return validateService.validateUsernameIsAvailable(username);
    }
}
