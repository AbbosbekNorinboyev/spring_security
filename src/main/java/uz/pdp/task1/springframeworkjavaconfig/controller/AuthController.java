package uz.pdp.task1.springframeworkjavaconfig.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.task1.springframeworkjavaconfig.daos.users.AuthUserDao;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthUser;
import uz.pdp.task1.springframeworkjavaconfig.dto.UserRegisterDTO;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/auth/login");
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logoutPage(ModelAndView modelAndView) {
        return "/auth/logout";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterDTO dto) {
        AuthUser user = AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        Long id = authUserDao.saveAuthUserReturnId(user);
        System.out.println("id: " + id);
        return "redirect:/auth/login";
    }
}
