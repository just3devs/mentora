package com.mentora.backend.security;

import com.mentora.backend.error.UserNotAuthenticatedException;
import com.mentora.backend.user.UserRepository;
import com.mentora.backend.user.model.Users;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String id = oauth2User.getAttribute("sub");
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String picture = oauth2User.getAttribute("picture");

        Users user = new Users();
        user.setId(UUID.fromString(id));
        user.setName(name);
        user.setEmail(email);
        user.setPicture(picture);

        userRepository.save(user);

        return oauth2User;
    }

    public String currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
            throw new UserNotAuthenticatedException();
        }

        OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();

        return oidcUser.getAttribute("oid");
    }

    public String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated");
        }

        OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();

        return oidcUser.getAttribute("name");
    }

    public String currentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated");
        }

        OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();

        return oidcUser.getAttribute("preferred_username");
    }
}
