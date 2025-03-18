package io.onicodes.issue_tracker.services;

import java.util.List;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.dtos.issue.IssueRequestDto;

public class IssueParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        
        var parameterType = parameterContext.getParameter().getType();
        return parameterType.equals(IssueRequestDto.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        var issueRequestDto = new IssueRequestDto();
        issueRequestDto.setTitle("test title");
        issueRequestDto.setDescription("test desc");
        issueRequestDto.setStatus("OPEN");
        issueRequestDto.setPriority("CRITICAL");

        var user = new AppUserDto();
        user.setId(Long.valueOf(1));
        user.setName("Oni");
        user.setEmail("myemail@gmail.com");
        
        issueRequestDto.setAssignees(List.of(user));
        return issueRequestDto;
    }
    
}
