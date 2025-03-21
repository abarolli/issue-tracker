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
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDto;
import io.onicodes.issue_tracker.dtos.issue.IssueResponseDto;
import io.onicodes.issue_tracker.services.IssueService;


@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public Page<IssueResponseDto> getIssues(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {

        return issueService.getIssues(page, size);
    }

    @GetMapping("/{id}")
    public IssueResponseDto getIssue(@PathVariable Long id) {
        return issueService.getIssue(id);
    }

    @PostMapping
    public IssueResponseDto createIssue(
        @Validated(CreateGroup.class) @RequestBody IssueRequestDto issueDto) {
        
        return issueService.createIssue(issueDto);
    }

    @PatchMapping("/{id}")
    public IssueResponseDto updateIssue(
        @PathVariable Long id,
        @RequestBody IssueRequestDto issueRequestDto) { // TODO: implement validation for update requests

        return issueService.updateIssue(id, issueRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }
}
