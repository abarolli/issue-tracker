package io.onicodes.issue_tracker.services;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import io.onicodes.issue_tracker.controllers.exceptions.IssueNotFoundException;
import io.onicodes.issue_tracker.dtos.IssueDTO;
import io.onicodes.issue_tracker.entityToDtoMappers.IssueMapper;
import io.onicodes.issue_tracker.repositories.IssueRepository;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public IssueDTO getIssue(Long id) {
        return issueRepository
                .findById(id)
                .map(issue -> IssueMapper.INSTANCE.issueToIssueDTO(issue))
                .orElseThrow(() -> new IssueNotFoundException(id));
    }

    public Page<IssueDTO> getIssues(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return issueRepository
                .findAll(pageable)
                .map(issue -> IssueMapper.INSTANCE.issueToIssueDTO(issue));
    }
}
