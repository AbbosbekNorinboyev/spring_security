package uz.pdp.task1.namedjdbctemplate;

import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Todo {
    private Integer id;
    private String title;
    private String priority;
    private LocalDateTime created_at;
}
