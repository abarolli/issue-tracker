package io.onicodes.issue_tracker.dtos.issue;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class IssueResponseDTO extends IssueBaseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
