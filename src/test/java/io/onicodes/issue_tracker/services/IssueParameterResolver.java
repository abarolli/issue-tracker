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
        var issueCreateRequestDTO = new IssueRequestDTO();
        issueCreateRequestDTO.setTitle("test title");
        issueCreateRequestDTO.setDescription("test desc");
        issueCreateRequestDTO.setStatus("OPEN");
        issueCreateRequestDTO.setPriority("CRITICAL");

        var user = new UserDTO();
        user.setId(Long.valueOf(1));
        user.setName("Oni");
        user.setEmail("myemail@gmail.com");
        
        issueCreateRequestDTO.setAssignees(List.of(user));
        return issueCreateRequestDTO;
    }
    
}
