package uz.pdp.task1.springframeworkjavaconfig.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;

@Controller
@ResponseBody
public class PrePostMethodLevelSecurityTestController {
    @GetMapping("/has_role_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "<h1>has_role_admin</h1>";
    }

    @GetMapping("/has_role_manager")
    @PreAuthorize("hasRole('MANAGER')")
//    @Secured("MANAGER") // Secured hasRole bilan bir xil ishlaydi
//    @RolesAllowed({"MANAGER", "ADMIN"})
    public String manager() {
        return "<h1>has_role_manager</h1>";
    }

    @GetMapping("/has_role_users")
    @PreAuthorize("hasRole('USER')")
    public String user() {
        return "<h1>has_role_user</h1>";
    }

    @GetMapping("/has_roles_manager_admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String managerAndAdmin() {
        return "<h1>has_roles_manager_admin</h1>";
    }

    @GetMapping("/has_roles_admin_manager_user")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public String adminManagerUser() {
        return "<h1>has_roles_admin_manager_user</h1>";
    }

//    @GetMapping("/delete_manager_page")
//    @PreAuthorize("hasAuthority('DELETE_MANAGER')")
//    public String deleteManagerPage() {
//        return "<h1>delete manager page</h1>";
//    }

    @GetMapping("/delete_manager_page")
    @PreAuthorize("hasAuthority(T(uz.pdp.task1.springframeworkjavaconfig.config.security.Permissions).HAS_PERMISSION_DELETE_MANAGER)")
    public String deleteManagerPage() {
        return "<h1>delete manager page</h1>";
    }

    @GetMapping("/block_user_delete_manager")
    @PreAuthorize("hasAnyAuthority('BLOCK_USER', 'DELETE_MANAGER')")
    public String manageManagerPage() {
        return "<h1>block user delete manager</h1>";
    }
}
// PreAuthorize va PostAuthorize lardan foydalansa bo'ladi ikkalasini vazifaisi bir xil
