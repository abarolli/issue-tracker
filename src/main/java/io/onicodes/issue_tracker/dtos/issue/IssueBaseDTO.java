package io.onicodes.issue_tracker.dtos.issue;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.onicodes.issue_tracker.dtos.CreateGroup;
import io.onicodes.issue_tracker.dtos.PatchGroup;
import io.onicodes.issue_tracker.dtos.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class IssueBaseDTO {
    @NotNull(groups = CreateGroup.class)
    @NotBlank(groups = {CreateGroup.class, PatchGroup.class})
    private String title;

    @NotNull(groups = CreateGroup.class)
    @NotBlank(groups = {CreateGroup.class, PatchGroup.class})
    private String description;

    @NotNull(groups = CreateGroup.class)
    private String status;

    @NotNull(groups = CreateGroup.class)
    private String priority;

    private List<UserDTO> assignees = new ArrayList<>();
}
