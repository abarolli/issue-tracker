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
import io.onicodes.issue_tracker.repositories.IssueRepository;
import io.onicodes.issue_tracker.repositories.AppUserRepository;

@Slf4j
@AllArgsConstructor
@Service
public class IssueAssigneeService {
    
    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;

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
        Set<IssueAssignee> currentAssignees = issue.getAssignees();
        Set<IssueAssignee> updatedAssignees = userRepository
            .findAllById(userIds)
            .stream()
            .map(user -> {
                return currentAssignees
                    .stream()
                    .filter(assignee -> assignee.getUser().getId().equals(user.getId()))
                    .findFirst()
                    .orElseGet(() -> {
                        IssueAssignee newAssignee = new IssueAssignee(issue, user);
                        currentAssignees.add(newAssignee);
                        return newAssignee;
                    });
            })
            .collect(Collectors.toSet());
        
        currentAssignees.retainAll(updatedAssignees);
        return currentAssignees;

    }

    public boolean isUserAssignedTo(Long userId, Set<IssueAssignee> assignees) {
        var currentAssigneeIds = assignees
                .stream()
                .map(assignee -> assignee.getUser().getId())
                .collect(Collectors.toSet());
        return currentAssigneeIds.contains(userId);
    }
}
