package io.personal.stock.service;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

import io.personal.stock.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {

    public void loginCheckAndInsertModel(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request,
            Model model);

    public List<Member> getAllUsers();
}
