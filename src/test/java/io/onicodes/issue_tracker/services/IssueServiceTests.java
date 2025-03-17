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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import io.onicodes.issue_tracker.dtos.issue.IssueRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.entityToDtoMappers.UserMapper;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.repositories.IssueAssigneeRepository;
import io.onicodes.issue_tracker.repositories.IssueRepository;
import io.onicodes.issue_tracker.repositories.UserRepository;


@ExtendWith(IssueParameterResolver.class)
@ExtendWith(MockitoExtension.class)
public class IssueServiceTests {
    
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private IssueAssigneeRepository issueAssigneeRepository;

    private IssueAssigneeService issueAssigneeService;
    private IssueService issueService;

    @BeforeEach
    public void setUpIssueAssigneeService() {
        issueAssigneeService = Mockito.spy(new IssueAssigneeService(userRepository, issueRepository, issueAssigneeRepository));
        issueService = new IssueService(issueRepository, issueAssigneeService);
    }

    private Issue dummySavedInitialIssueFrom(IssueRequestDTO issueCreateRequestDTO) {
        Issue issue = IssueMapper.INSTANCE.issueCreateRequestDTOToIssue(issueCreateRequestDTO);
        issue.setId(Long.valueOf(1));
        return issue;
    }

    private Issue dummySavedIssueWithAssigneesFrom(IssueRequestDTO issueCreateRequestDTO) {
        Issue issue = IssueMapper.INSTANCE.issueCreateRequestDTOToIssue(issueCreateRequestDTO);
        issue.setId(Long.valueOf(1));
        Set<IssueAssignee> assignees = issueCreateRequestDTO.getAssignees()
                            .stream()
                            .map(userDTO ->
                                new IssueAssignee(issue, UserMapper.INSTANCE.userDTOToUser(userDTO)))
                            .collect(Collectors.toSet());
        
        issue.setAssignees(assignees);
        return issue;      
    }

    private void assertValidResponseDTO(IssueResponseDTO responseDTO, IssueRequestDTO requestDTO) {
        assert(responseDTO != null);
        assert(responseDTO.getTitle().equals(requestDTO.getTitle()));
        assert(responseDTO.getDescription().equals(requestDTO.getDescription()));
        assert(responseDTO).getAssignees().equals(requestDTO.getAssignees());
    }

    @Test
    public void shouldGetIssue(IssueRequestDTO issueCreateRequestDTO) {
        when(issueRepository.findById(anyLong()))
            .thenReturn(Optional.of(dummySavedIssueWithAssigneesFrom(issueCreateRequestDTO)));
        
        var issueResponseDTO = issueService.getIssue(Long.valueOf(1));
        assertValidResponseDTO(issueResponseDTO, issueCreateRequestDTO);
    }

    @Test
    public void shouldGetIssues(IssueRequestDTO issueCreateRequestDTO) {
        when(issueRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(List.of(dummySavedIssueWithAssigneesFrom(issueCreateRequestDTO))));

        List<IssueResponseDTO> issues = issueService.getIssues(0, 10).getContent();
        for (var issueResponseDTO : issues) {
            assertValidResponseDTO(issueResponseDTO, issueCreateRequestDTO);
        }
    }
    
    @Test
    public void shouldCreateIssue(IssueRequestDTO issueCreateRequestDTO) {

        when(userRepository.findAllById(anyIterable()))
            .thenReturn(UserMapper.INSTANCE
                .userDTOListToUserList(issueCreateRequestDTO.getAssignees()));

        when(issueRepository.save(any()))
            .thenReturn(dummySavedInitialIssueFrom(issueCreateRequestDTO));
        
        var issueResponseDTO = issueService.createIssue(issueCreateRequestDTO);
        assertValidResponseDTO(issueResponseDTO, issueCreateRequestDTO);
    }
}
