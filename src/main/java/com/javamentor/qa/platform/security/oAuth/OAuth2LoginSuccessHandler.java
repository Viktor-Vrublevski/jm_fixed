package com.javamentor.qa.platform.security.oAuth;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.SecurityConfig;
import com.javamentor.qa.platform.security.jwt.JwtUtils;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;
    @Autowired
    OAuth2LoginSuccessHandler(UserService userService, JwtUtils jwtUtils, RoleService roleService){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.roleService = roleService;
    }
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuthUser = (CustomOAuth2User) authentication.getPrincipal();
        Optional userOptional = userService.getUserByEmail(oAuthUser.getId());
        Optional<Role> role = roleService.getRoleByName("USER");
        if(!userOptional.isPresent()){
            User user = new User();
            user.setPassword(generatePassword());
            user.setEmail(oAuthUser.getId());
            user.setIsEnabled(true);
            user.setRole(role.get());
            userService.persist(user);
        }
        Cookie cookie = new Cookie("token","Bearer_" + jwtUtils.generateJwtTokenOAuth(oAuthUser.getId()));
        cookie.setPath("/");
        response.addCookie(cookie);
        this.handle(request,response);

    }

    private String generatePassword(){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rnd.nextInt(AB.length()); i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        System.out.println(sb.toString() + " сгенерированны пароль");
        return sb.toString();
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        String targetUrl = "http://localhost:5557/site";
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
