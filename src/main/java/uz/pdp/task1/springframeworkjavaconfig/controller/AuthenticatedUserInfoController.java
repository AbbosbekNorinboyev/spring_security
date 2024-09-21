package uz.pdp.task1.springframeworkjavaconfig.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.task1.springframeworkjavaconfig.config.security.CustomUserDetails;
import uz.pdp.task1.springframeworkjavaconfig.config.security.SessionUser;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthUser;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class AuthenticatedUserInfoController {
    private final SessionUser sessionUser;

    @GetMapping("/create/blog")
    public String createBlog(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        AuthUser authUser = customUserDetails.getAuthUser();
        customUserDetails.getAuthorities().forEach(System.out::println);
        System.out.println(authUser.getUsername());
        System.out.println("id: " + sessionUser.getUserId());
        return "Authhenticated user info";
    }
}
