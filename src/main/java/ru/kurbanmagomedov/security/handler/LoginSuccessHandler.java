package ru.kurbanmagomedov.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kurbanmagomedov.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    public LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

//        User user = userService.getUserByUsername(httpServletRequest.getParameter("j_username"));
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/users");
        } else {
            httpServletResponse.sendRedirect("/user");
        }
    }
}