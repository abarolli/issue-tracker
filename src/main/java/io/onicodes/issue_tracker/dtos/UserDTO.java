package io.onicodes.issue_tracker.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import io.onicodes.issue_tracker.models.User;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
