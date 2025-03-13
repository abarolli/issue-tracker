package io.onicodes.issue_tracker.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public IssueResponseDTO createIssue(@RequestBody IssueRequestDTO issueDTO) {
        return issueService.createIssue(issueDTO);
    }
}
