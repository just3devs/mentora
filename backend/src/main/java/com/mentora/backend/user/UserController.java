package com.mentora.backend.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/auth-info")
    public Map<String, Object> getAuthInfo(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> authInfo = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            authInfo.put("authenticated", true);
            authInfo.put("authenticationType", "OAuth2");
            authInfo.put("authorizedClientRegistrationId", oauthToken.getAuthorizedClientRegistrationId());

            if (principal instanceof OidcUser oidcUser) {
                authInfo.put("principalType", "OidcUser");
                authInfo.put("userId", oidcUser.getAttribute("sub"));
                authInfo.put("email", oidcUser.getAttribute("email"));
                authInfo.put("name", oidcUser.getAttribute("name"));
                authInfo.put("picture", oidcUser.getAttribute("picture"));

                // ID Token - Postman için kullanabileceğiniz token
                authInfo.put("idToken", oidcUser.getIdToken().getTokenValue());
                authInfo.put("tokenExpiresAt", oidcUser.getIdToken().getExpiresAt());

                // Tüm claims
                authInfo.put("allClaims", oidcUser.getClaims());

                // Authorities
                authInfo.put("authorities", authentication.getAuthorities().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()));
            } else {
                authInfo.put("principalType", "OAuth2User");
                authInfo.put("attributes", principal.getAttributes());
            }
        } else {
            authInfo.put("authenticated", false);
            authInfo.put("message", "Not authenticated or not OAuth2");
        }

        return authInfo;
    }
}