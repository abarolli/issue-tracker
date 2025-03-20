package io.onicodes.issue_tracker.entityToDtoMappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.dtos.RegistrationRequestDto;
import io.onicodes.issue_tracker.models.appUser.AppUser;

@Mapper
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(target = "issues", ignore = true)
    @Mapping(target = "password", ignore = true)
    AppUser appUserDtoToAppUser(AppUserDto userDto);
    
    AppUserDto appUserToAppUserDto(AppUser user);

    List<AppUser> appUserDtoListToAppUserList(List<AppUserDto> userDtos);

    @Mapping(target = "issues", ignore = true)
    @Mapping(target = "roles", ignore = true)
    AppUser registrationRequestDtoToAppUser(RegistrationRequestDto requestDto);
}
