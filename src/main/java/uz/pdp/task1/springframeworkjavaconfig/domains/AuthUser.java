package uz.pdp.task1.springframeworkjavaconfig.domains;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthUser {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime created_at;
    private List<AuthRole> roles;
}
