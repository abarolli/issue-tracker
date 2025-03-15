package io.onicodes.issue_tracker.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.repositories.IssueRepository;
import io.onicodes.issue_tracker.repositories.UserRepository;

@AllArgsConstructor
@Service
public class IssueAssigneeService {
    
    @Autowired
    private UserRepository userRepository;
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
}
