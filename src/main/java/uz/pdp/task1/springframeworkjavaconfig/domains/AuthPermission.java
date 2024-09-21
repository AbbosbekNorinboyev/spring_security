package uz.pdp.task1.springframeworkjavaconfig.domains;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthPermission {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime created_at;
}
