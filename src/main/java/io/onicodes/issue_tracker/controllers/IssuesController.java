package io.onicodes.issue_tracker.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.issue_tracker.models.Issue;
import io.onicodes.issue_tracker.daos.IssuesRepository;


@RestController
@RequestMapping("/issues")
public class IssuesController {
    private final IssuesRepository issuesRepository;


    public IssuesController(IssuesRepository issuesRepository) {
        this.issuesRepository = issuesRepository;
    }

    @GetMapping
    public Page<Issue> getIssues(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return issuesRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Issue getIssue(@PathVariable Long id) {
        var optionalIssue = issuesRepository.findById(id);
        if (optionalIssue.isEmpty())
            return null; // TODO: handle non existent issue
        return optionalIssue.get();
    }

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        return issuesRepository.save(issue);
    }
}
