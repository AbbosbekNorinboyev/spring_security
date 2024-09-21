package uz.pdp.task1.springframeworkjavaconfig.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.task1.springframeworkjavaconfig.daos.permissions.AuthPermissionDao;
import uz.pdp.task1.springframeworkjavaconfig.daos.roles.AuthRoleDao;
import uz.pdp.task1.springframeworkjavaconfig.daos.users.AuthUserDao;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthPermission;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthRole;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthUser;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthUserDao authUserDao;
    private final AuthRoleDao authRoleDao;
    private final AuthPermissionDao authPermissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found by username '%s': ".formatted(username)));
        System.out.println(authUser);
        Long id = authUser.getId();
        List<AuthRole> roles = authRoleDao.findAllAuthRoleByUserId(id);
        for (AuthRole role : roles) {
            List<AuthPermission> permissions = authPermissionDao.findAllAuthPermissionById(role.getId());
            role.setPermissions(permissions);
        }
        authUser.setRoles(roles);
        return new CustomUserDetails(authUser);
    }

}
