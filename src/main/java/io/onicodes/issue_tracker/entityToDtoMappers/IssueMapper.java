package io.onicodes.issue_tracker.entityToDtoMappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.IssueDTO;
import io.onicodes.issue_tracker.dtos.UserDTO;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;


@Mapper
public interface IssueMapper {

    IssueMapper INSTANCE = Mappers.getMapper(IssueMapper.class);
    
    Issue issueDTOToIssue(IssueDTO issueDTO);

    default List<UserDTO> setOfIssueAssigneeToListOfUserDTO(Set<IssueAssignee> assignees) {
        if (assignees == null) return null;
        return assignees.stream()
                .map(a -> new UserDTO(a.getUser()))
                .collect(Collectors.toList());
    }

    IssueDTO issueToIssueDTO(Issue issue);
}
