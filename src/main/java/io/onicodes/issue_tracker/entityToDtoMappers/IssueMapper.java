package io.onicodes.issue_tracker.entityToDtoMappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.models.AppUser;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;


@Mapper
public interface IssueMapper {

    IssueMapper INSTANCE = Mappers.getMapper(IssueMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignees", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Issue issueRequestDTOToIssue(IssueRequestDTO issueDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignees", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(IssueRequestDTO issueRequestDTO, @MappingTarget Issue issue);

    IssueResponseDTO issueToIssueDTO(Issue issue);

    AppUserDto userToUserDto(AppUser user);

    default AppUserDto issueAssigneeToUserDTO(IssueAssignee assignee) {
        return userToUserDto(assignee.getUser());
    }

}
