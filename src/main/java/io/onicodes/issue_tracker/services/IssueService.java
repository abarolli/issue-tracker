package io.onicodes.issue_tracker.services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

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
import io.onicodes.issue_tracker.repositories.IssueRepository;


@AllArgsConstructor
@Service
public class IssueService {
    
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueAssigneeService issueAssigneeService;

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
                        .save(IssueMapper.INSTANCE.issueRequestDTOToIssue(issueDTO));
        List<Long> userIds = issueDTO
                                .getAssignees()
                                .stream()
                                .map(userDTO -> userDTO.getId())
                                .collect(Collectors.toList());
        
        issueAssigneeService.assign(userIds, issue);
        return IssueMapper.INSTANCE.issueToIssueDTO(issue);
    }
    
    @Transactional
    public IssueResponseDTO updateIssue(Long id, IssueRequestDTO issueDTO) {
        Issue issue = issueRepository.findById(id)
                                    .orElseThrow(() -> new IssueNotFoundException(id));
        
        IssueMapper.INSTANCE.updateFromDTO(issueDTO, issue);

        List<Long> userIds = issueDTO
                                .getAssignees()
                                .stream()
                                .map(userDTO -> userDTO.getId())
                                .collect(Collectors.toList());
        
        issueAssigneeService.updateAssigneesForIssue(userIds, issue);
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepository.save(issue);
        return IssueMapper.INSTANCE.issueToIssueDTO(issue);
    }
}
