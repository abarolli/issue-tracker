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

import io.onicodes.issue_tracker.dtos.IssueDTO;
import io.onicodes.issue_tracker.services.IssueService;


@RestController
@RequestMapping("/issues")
public class IssuesController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public Page<IssueDTO> getIssues(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {

        return issueService.getIssues(page, size);
    }

    @GetMapping("/{id}")
    public IssueDTO getIssue(@PathVariable Long id) {
        return issueService.getIssue(id);
    }

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        return issuesRepository.save(issue);
    }
}
