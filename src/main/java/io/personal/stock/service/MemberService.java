package io.personal.stock.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {

    public void loginCheckAndInsertModel(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request,
            Model model);

}
