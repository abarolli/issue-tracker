package io.onicodes.issue_tracker.entityToDtoMappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.UserDTO;
import io.onicodes.issue_tracker.models.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "issues", ignore = true)
    User userDTOToUser(UserDTO userDTO);

    List<User> userDTOListToUserList(List<UserDTO> userDTOs);
}
