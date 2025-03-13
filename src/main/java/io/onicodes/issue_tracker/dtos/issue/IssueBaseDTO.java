package io.onicodes.issue_tracker.dtos.issue;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import io.onicodes.issue_tracker.dtos.UserDTO;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class IssueBaseDTO {
    private String title;
    private String description;
    private String status;
    private String priority;
    private List<UserDTO> assignees;

}
