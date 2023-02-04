package com.example.modularquizappmvc.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = null;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
//            System.out.println("role " + grantedAuthority.getAuthority());
            if (grantedAuthority.getAuthority().equals("TEACHER")) {
                redirectUrl = "teach/page";
                break;
            } else if (grantedAuthority.getAuthority().equals("STUDENT")) {
                redirectUrl = "/student/page";
                break;
            }
        }
//        System.out.println("redirectUrl " + redirectUrl);
        if (redirectUrl == null) {
            throw new IllegalStateException();
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
