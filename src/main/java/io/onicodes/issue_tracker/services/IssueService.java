package io.onicodes.issue_tracker.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import io.onicodes.issue_tracker.controllers.exceptions.IssueNotFoundException;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.repositories.IssueRepository;


@Service
public class IssueService {
    
    @Autowired
    private IssueRepository issueRepository;

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

    public IssueResponseDTO createIssue(IssueRequestDTO issueDTO) {
        return IssueMapper
                .INSTANCE
                .issueToIssueDTO(issueRepository
                    .save(IssueMapper.INSTANCE.issueDTOToIssue(issueDTO)));
    }
}
