package io.onicodes.issue_tracker.services;

import java.util.List;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import io.onicodes.issue_tracker.dtos.UserDTO;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDTO;

public class IssueParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        
        var parameterType = parameterContext.getParameter().getType();
        return parameterType.equals(IssueRequestDTO.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        var issueRequestDTO = new IssueRequestDTO();
        issueRequestDTO.setTitle("test title");
        issueRequestDTO.setDescription("test desc");
        issueRequestDTO.setStatus("OPEN");
        issueRequestDTO.setPriority("CRITICAL");

        var user = new UserDTO();
        user.setId(Long.valueOf(1));
        user.setName("Oni");
        user.setEmail("myemail@gmail.com");
        
        issueRequestDTO.setAssignees(List.of(user));
        return issueRequestDTO;
    }
    
}
