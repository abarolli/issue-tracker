package io.onicodes.issue_tracker.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssigneeId;
import io.onicodes.issue_tracker.repositories.IssueAssigneeRepository;
import io.onicodes.issue_tracker.repositories.IssueRepository;
import io.onicodes.issue_tracker.repositories.UserRepository;

@Slf4j
@AllArgsConstructor
@Service
public class IssueAssigneeService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueAssigneeRepository issueAssigneeRepository;

    @Transactional
    public Set<IssueAssignee> assign(List<Long> userIds, Issue issue) {
        
        Set<IssueAssignee> assignees = userRepository
                            .findAllById(userIds)
                            .stream()
                            .map(user -> new IssueAssignee(issue, user))
                            .collect(Collectors.toSet());
        
        issue.setAssignees(assignees);
        issueRepository.save(issue);
        return assignees;
    }

    @Transactional
    public Set<IssueAssignee> updateAssigneesForIssue(List<Long> userIds, Issue issue) {
        // get existing assignees
        var existingIssueAssigneeIds = userIds
            .stream()
            .filter(userId -> isUserAssignedTo(userId, issue))
            .map(userId -> new IssueAssigneeId(issue.getId(), userId))
            .collect(Collectors.toList());
        var existingAssignees = issueAssigneeRepository.findAllById(existingIssueAssigneeIds);

        // get new assignees
        var newAssignees = userIds
            .stream()
            .filter(userId -> !isUserAssignedTo(userId, issue))
            .map(userId -> new IssueAssignee(issue, userRepository.findById(userId).get()))
            .collect(Collectors.toList());

        var currentAssignees = issue.getAssignees();
        currentAssignees.retainAll(existingAssignees);
        currentAssignees.addAll(newAssignees);
        issue.setAssignees(currentAssignees);
        return currentAssignees;
    }

    public boolean isUserAssignedTo(Long userId, Issue issue) {
        var currentAssigneeIds = issue
                .getAssignees()
                .stream()
                .map(assignee -> assignee.getUser().getId())
                .collect(Collectors.toSet());
        return currentAssigneeIds.contains(userId);
    }
}
