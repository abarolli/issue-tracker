package io.onicodes.issue_tracker.dtos;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class AppUserDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Set<String> roles = new HashSet<>();
}
