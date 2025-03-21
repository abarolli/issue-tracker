package io.onicodes.issue_tracker.entityToDtoMappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.dtos.RegistrationRequestDto;
import io.onicodes.issue_tracker.models.appUser.AppUser;
import io.onicodes.issue_tracker.models.appUser.Role;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    @Mapping(target = "issues", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    AppUser appUserDtoToAppUser(AppUserDto userDto);
    
    AppUserDto appUserToAppUserDto(AppUser user);

    List<AppUser> appUserDtoListToAppUserList(List<AppUserDto> userDtos);

    default String roleToString(Role role) {
        return role.getName();
    }

    List<String> roleSetToStringList(Set<Role> roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "issues", ignore = true)
    @Mapping(target = "roles", ignore = true)
    AppUser registrationRequestDtoToAppUser(RegistrationRequestDto requestDto);

}
