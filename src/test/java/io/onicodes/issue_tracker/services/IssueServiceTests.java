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

import io.onicodes.issue_tracker.dtos.UserDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.entityToDtoMappers.UserMapper;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.User;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.repositories.IssueRepository;
import io.onicodes.issue_tracker.repositories.UserRepository;


@ExtendWith(IssueParameterResolver.class)
@ExtendWith(MockitoExtension.class)
public class IssueServiceTests {
    
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private UserRepository userRepository;

    private IssueAssigneeService issueAssigneeService;
    private IssueService issueService;

    @BeforeEach
    public void setUpIssueAssigneeService() {
        issueAssigneeService = Mockito.spy(new IssueAssigneeService(userRepository, issueRepository));
        issueService = new IssueService(issueRepository, issueAssigneeService);
    }

    private Issue dummySavedInitialIssueFrom(IssueRequestDTO issueRequestDTO) {
        Issue issue = IssueMapper.INSTANCE.issueRequestDTOToIssue(issueRequestDTO);
        issue.setId(Long.valueOf(1));
        return issue;
    }

    private Issue dummySavedIssueWithAssigneesFrom(IssueRequestDTO issueRequestDTO) {
        Issue issue = IssueMapper.INSTANCE.issueRequestDTOToIssue(issueRequestDTO);
        issue.setId(Long.valueOf(1));
        Set<IssueAssignee> assignees = issueRequestDTO.getAssignees()
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
        assert(new HashSet<>(responseDTO.getAssignees())
            .equals(new HashSet<>(requestDTO.getAssignees())));
    }

    @Test
    public void shouldGetIssue(IssueRequestDTO issueRequestDTO) {
        when(issueRepository.findById(anyLong()))
            .thenReturn(Optional.of(dummySavedIssueWithAssigneesFrom(issueRequestDTO)));
        
        var issueResponseDTO = issueService.getIssue(Long.valueOf(1));
        assertValidResponseDTO(issueResponseDTO, issueRequestDTO);
    }

    @Test
    public void shouldGetIssues(IssueRequestDTO issueRequestDTO) {
        when(issueRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(List.of(dummySavedIssueWithAssigneesFrom(issueRequestDTO))));

        List<IssueResponseDTO> issues = issueService.getIssues(0, 10).getContent();
        for (var issueResponseDTO : issues) {
            assertValidResponseDTO(issueResponseDTO, issueRequestDTO);
        }
    }
    
    @Test
    public void shouldCreateIssue(IssueRequestDTO issueRequestDTO) {

        when(userRepository.findAllById(anyIterable()))
            .thenReturn(UserMapper.INSTANCE
                .userDTOListToUserList(issueRequestDTO.getAssignees()));

        when(issueRepository.save(any()))
            .thenReturn(dummySavedInitialIssueFrom(issueRequestDTO));
        
        var issueResponseDTO = issueService.createIssue(issueRequestDTO);
        assertValidResponseDTO(issueResponseDTO, issueRequestDTO);
    }

    @Test
    public void shouldUpdateIssue(IssueRequestDTO issueRequestDTO) {
        Issue existingIssue = dummySavedIssueWithAssigneesFrom(issueRequestDTO);
        when(issueRepository.findById(1L)).thenReturn(Optional.of(existingIssue));

        UserDTO newAssignee = new UserDTO();
        newAssignee.setId(2L);
        newAssignee.setEmail("newemail@gmail.com");

        List<UserDTO> updatedAssignees = new ArrayList<>(issueRequestDTO.getAssignees());
        updatedAssignees.add(newAssignee);
        issueRequestDTO.setAssignees(updatedAssignees);
        
        List<User> allUsers = UserMapper.INSTANCE.userDTOListToUserList(updatedAssignees);
        when(userRepository.findAllById(anyIterable())).thenReturn(allUsers);

        when(issueRepository.save(any())).thenReturn(existingIssue);

        var issueResponseDTO = issueService.updateIssue(1L, issueRequestDTO);
        assertValidResponseDTO(issueResponseDTO, issueRequestDTO);
    }
}
