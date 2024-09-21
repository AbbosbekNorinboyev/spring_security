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
public class AuthRole {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime created_at;
    private List<AuthPermission> permissions;
}
