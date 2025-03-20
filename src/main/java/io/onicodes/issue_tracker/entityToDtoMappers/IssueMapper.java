package io.onicodes.issue_tracker.entityToDtoMappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDto;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDto;
import io.onicodes.issue_tracker.models.appUser.AppUser;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;


@Mapper
public interface IssueMapper {

    IssueMapper INSTANCE = Mappers.getMapper(IssueMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignees", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Issue issueRequestDtoToIssue(IssueRequestDto issueDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignees", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(IssueRequestDto issueRequestDto, @MappingTarget Issue issue);

    IssueResponseDto issueToIssueDto(Issue issue);

    AppUserDto userToUserDto(AppUser user);

    default AppUserDto issueAssigneeToUserDto(IssueAssignee assignee) {
        return userToUserDto(assignee.getUser());
    }

}
