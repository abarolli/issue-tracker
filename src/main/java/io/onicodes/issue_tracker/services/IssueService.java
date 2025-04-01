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
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDto;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDto;
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

    public IssueResponseDto getIssue(Long id) {
        return issueRepository
                .findById(id)
                .map(issue -> IssueMapper.INSTANCE.issueToIssueDto(issue))
                .orElseThrow(() -> new IssueNotFoundException(id));
    }

    public Page<IssueResponseDto> getIssues(int page, int size) {
        page = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(page, size);
        return issueRepository
                .findAll(pageable)
                .map(issue -> IssueMapper.INSTANCE.issueToIssueDto(issue));
    }

    @Transactional
    public IssueResponseDto createIssue(IssueRequestDto issueDto) {
        Issue issue = issueRepository
                        .save(IssueMapper.INSTANCE.issueRequestDtoToIssue(issueDto));
        List<Long> userIds = issueDto
                                .getAssignees()
                                .stream()
                                .map(userDto -> userDto.getId())
                                .collect(Collectors.toList());
        
        issueAssigneeService.assign(userIds, issue);
        return IssueMapper.INSTANCE.issueToIssueDto(issue);
    }
    
    @Transactional
    public IssueResponseDto updateIssue(Long id, IssueRequestDto issueDto) {
        Issue issue = issueRepository.findById(id)
                                    .orElseThrow(() -> new IssueNotFoundException(id));
        
        IssueMapper.INSTANCE.updateFromDto(issueDto, issue);
        var assignees = issueDto.getAssignees();
        if (assignees != null) {
            List<Long> userIds = assignees
                .stream()
                .map(userDto -> userDto.getId())
                .collect(Collectors.toList());

            issueAssigneeService.updateAssigneesForIssue(userIds, issue);
        }
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepository.save(issue);
        return IssueMapper.INSTANCE.issueToIssueDto(issue);
    }

    @Transactional
    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }
}
