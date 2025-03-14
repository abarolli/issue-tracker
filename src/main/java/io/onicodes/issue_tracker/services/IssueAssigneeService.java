package io.onicodes.issue_tracker.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.onicodes.issue_tracker.models.issue.Issue;
import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;
import io.onicodes.issue_tracker.repositories.IssueAssigneeRepository;
import io.onicodes.issue_tracker.repositories.UserRepository;

@Service
public class IssueAssigneeService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueAssigneeRepository issueAssigneeRepository;

    @Transactional
    public Set<IssueAssignee> assign(List<Long> userIds, Issue issue) {
        
        Set<IssueAssignee> assignees = userRepository
                            .findAllById(userIds)
                            .stream()
                            .map(user -> new IssueAssignee(issue, user))
                            .collect(Collectors.toSet());
        
        issueAssigneeRepository.saveAll(assignees);
        issue.setAssignees(assignees);
        return assignees;
    }
}
