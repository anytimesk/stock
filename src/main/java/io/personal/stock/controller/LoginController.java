package io.personal.stock.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LoginController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {

        log.debug(String.format("Call Log In Page, CsrfToken class getName : %s", CsrfToken.class.getName()));

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.addAttribute("csrfToken", csrfToken.getToken());
        model.addAttribute("csrfParameterName", csrfToken.getParameterName());
        log.debug(String.format("CSRF Token : %s", csrfToken.getToken().toString()));
        log.debug(String.format("csrfParameterName : %s", csrfToken.getParameterName()));

        return "login";
    }
}
