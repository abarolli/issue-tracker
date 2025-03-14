package io.onicodes.issue_tracker.entityToDtoMappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.UserDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueCreateRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.models.User;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;


@Mapper
public interface IssueMapper {

    IssueMapper INSTANCE = Mappers.getMapper(IssueMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignees", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Issue issueDTOToIssue(IssueCreateRequestDTO issueDTO);

    IssueResponseDTO issueToIssueDTO(Issue issue);

    UserDTO userToUserDTO(User user);

    default List<UserDTO> setOfIssueAssigneeToListOfUserDTO(Set<IssueAssignee> assignees) {
        if (assignees == null) return null;
        return assignees.stream()
                .map(assignee -> 
                    userToUserDTO(assignee.getUser()))
                .collect(Collectors.toList());
    }

}
