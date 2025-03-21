package io.onicodes.issue_tracker.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDto;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDto;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.entityToDtoMappers.AppUserMapper;
import io.onicodes.issue_tracker.models.appUser.AppUser;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.repositories.IssueRepository;
import io.onicodes.issue_tracker.repositories.AppUserRepository;


@ExtendWith(IssueParameterResolver.class)
@ExtendWith(MockitoExtension.class)
public class IssueServiceTests {
    
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private AppUserRepository userRepository;

    private IssueAssigneeService issueAssigneeService;
    private IssueService issueService;

    @BeforeEach
    public void setUpIssueAssigneeService() {
        issueAssigneeService = Mockito.spy(new IssueAssigneeService(userRepository, issueRepository));
        issueService = new IssueService(issueRepository, issueAssigneeService);
    }

    private Issue dummySavedInitialIssueFrom(IssueRequestDto issueRequestDto) {
        Issue issue = IssueMapper.INSTANCE.issueRequestDtoToIssue(issueRequestDto);
        issue.setId(Long.valueOf(1));
        return issue;
    }

    private Issue dummySavedIssueWithAssigneesFrom(IssueRequestDto issueRequestDto) {
        Issue issue = IssueMapper.INSTANCE.issueRequestDtoToIssue(issueRequestDto);
        issue.setId(Long.valueOf(1));
        Set<IssueAssignee> assignees = issueRequestDto.getAssignees()
                            .stream()
                            .map(userDto ->
                                new IssueAssignee(issue, AppUserMapper.INSTANCE.appUserDtoToAppUser(userDto)))
                            .collect(Collectors.toSet());
        
        issue.setAssignees(assignees);
        return issue;      
    }

    private void assertValidResponseDto(IssueResponseDto responseDto, IssueRequestDto requestDto) {
        assert(responseDto != null);
        assert(responseDto.getTitle().equals(requestDto.getTitle()));
        assert(responseDto.getDescription().equals(requestDto.getDescription()));
        assert(new HashSet<>(responseDto.getAssignees())
            .equals(new HashSet<>(requestDto.getAssignees())));
    }

    @Test
    public void shouldGetIssue(IssueRequestDto issueRequestDto) {
        when(issueRepository.findById(anyLong()))
            .thenReturn(Optional.of(dummySavedIssueWithAssigneesFrom(issueRequestDto)));
        
        var issueResponseDto = issueService.getIssue(Long.valueOf(1));
        assertValidResponseDto(issueResponseDto, issueRequestDto);
    }

    @Test
    public void shouldGetIssues(IssueRequestDto issueRequestDto) {
        when(issueRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(List.of(dummySavedIssueWithAssigneesFrom(issueRequestDto))));

        List<IssueResponseDto> issues = issueService.getIssues(0, 10).getContent();
        for (var issueResponseDto : issues) {
            assertValidResponseDto(issueResponseDto, issueRequestDto);
        }
    }
    
    @Test
    public void shouldCreateIssue(IssueRequestDto issueRequestDto) {

        when(userRepository.findAllById(anyIterable()))
            .thenReturn(AppUserMapper.INSTANCE
                .appUserDtoListToAppUserList(issueRequestDto.getAssignees()));

        when(issueRepository.save(any()))
            .thenReturn(dummySavedInitialIssueFrom(issueRequestDto));
        
        var issueResponseDto = issueService.createIssue(issueRequestDto);
        assertValidResponseDto(issueResponseDto, issueRequestDto);
    }

    @Test
    public void shouldUpdateIssue(IssueRequestDto issueRequestDto) {
        Issue existingIssue = dummySavedIssueWithAssigneesFrom(issueRequestDto);
        when(issueRepository.findById(1L)).thenReturn(Optional.of(existingIssue));

        AppUserDto newAssignee = new AppUserDto();
        newAssignee.setId(2L);
        newAssignee.setEmail("newemail@gmail.com");

        List<AppUserDto> updatedAssignees = new ArrayList<>(issueRequestDto.getAssignees());
        updatedAssignees.add(newAssignee);
        issueRequestDto.setAssignees(updatedAssignees);
        
        List<AppUser> allUsers = AppUserMapper.INSTANCE.appUserDtoListToAppUserList(updatedAssignees);
        when(userRepository.findAllById(anyIterable())).thenReturn(allUsers);

        when(issueRepository.save(any())).thenReturn(existingIssue);

        var issueResponseDto = issueService.updateIssue(1L, issueRequestDto);
        assertValidResponseDto(issueResponseDto, issueRequestDto);
    }
}
