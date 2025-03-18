package io.onicodes.issue_tracker.entityToDtoMappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.models.AppUser;

@Mapper
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(target = "issues", ignore = true)
    AppUser appUserDtoToAppUser(AppUserDto userDTO);

    List<AppUser> appUserDtoListToAppUserList(List<AppUserDto> userDTOs);
}
