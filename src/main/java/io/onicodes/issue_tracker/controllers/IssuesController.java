package io.onicodes.issue_tracker.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.issue_tracker.dtos.CreateGroup;
import io.onicodes.issue_tracker.dtos.PatchGroup;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDTO;
import io.onicodes.issue_tracker.services.IssueService;


@RestController
@RequestMapping("/issues")
public class IssuesController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public Page<IssueResponseDTO> getIssues(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {

        return issueService.getIssues(page, size);
    }

    @GetMapping("/{id}")
    public IssueResponseDTO getIssue(@PathVariable Long id) {
        return issueService.getIssue(id);
    }

    @PostMapping
    public IssueResponseDTO createIssue(
        @Validated(CreateGroup.class) @RequestBody IssueRequestDTO issueDTO) {
        
        return issueService.createIssue(issueDTO);
    }

    @PatchMapping("/{id}")
    public IssueResponseDTO updateIssue(
        @PathVariable Long id,
        @RequestBody IssueRequestDTO issueRequestDTO) { // TODO: implement validation for update requests

        return issueService.updateIssue(id, issueRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }
}
