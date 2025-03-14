package io.onicodes.issue_tracker.services;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.onicodes.issue_tracker.controllers.exceptions.IssueNotFoundException;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.repositories.IssueAssigneeRepository;
import io.onicodes.issue_tracker.repositories.IssueRepository;
import io.onicodes.issue_tracker.repositories.UserRepository;


@Service
public class IssueService {
    
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueAssigneeRepository issueAssigneeRepository;

    public IssueResponseDTO getIssue(Long id) {
        return issueRepository
                .findById(id)
                .map(issue -> IssueMapper.INSTANCE.issueToIssueDTO(issue))
                .orElseThrow(() -> new IssueNotFoundException(id));
    }

    public Page<IssueResponseDTO> getIssues(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return issueRepository
                .findAll(pageable)
                .map(issue -> IssueMapper.INSTANCE.issueToIssueDTO(issue));
    }

    @Transactional
    public IssueResponseDTO createIssue(IssueRequestDTO issueDTO) {
        Issue issue = issueRepository
                        .save(IssueMapper.INSTANCE.issueDTOToIssue(issueDTO));
        List<Long> userIds = issueDTO
                                .getAssignees()
                                .stream()
                                .map(userDTO -> userDTO.getId())
                                .collect(Collectors.toList());
        
        Set<IssueAssignee> assignees = userRepository
                            .findAllById(userIds)
                            .stream()
                            .map(user -> new IssueAssignee(issue, user))
                            .collect(Collectors.toSet());
        
        issueAssigneeRepository.saveAll(assignees);
        issue.setAssignees(assignees);
        return IssueMapper.INSTANCE.issueToIssueDTO(issue);
    }
}
