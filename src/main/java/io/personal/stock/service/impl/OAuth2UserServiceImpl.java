package io.personal.stock.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import io.personal.stock.entity.Member;
import io.personal.stock.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    @Autowired
    private final MemberRepository memberRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");

        log.info("E-Mail : {}, \t Registration ID : {}", email, registrationId);

        Optional<Member> existMember = memberRepository.findByEmail(email);

        existMember.ifPresentOrElse(member -> {
            // Login시 계정 정보 Update하도록 하는 샘플 Code
            // 필요하면 기능 추가
            memberRepository.save(member);
        }, () -> {
            // Login시 계정 정보 없으면 추가하는 코드
            Member member = new Member();

            member.setName(oAuth2User.getAttribute("name"));
            member.setEmail(email);
            member.setOauth2Vender(registrationId);

            memberRepository.save(member);
        });

        return oAuth2User;
    }
}
