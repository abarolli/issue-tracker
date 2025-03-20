package io.onicodes.issue_tracker.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.onicodes.issue_tracker.dtos.AppUserDto;
import io.onicodes.issue_tracker.dtos.RegistrationRequestDto;
import io.onicodes.issue_tracker.entityToDtoMappers.AppUserMapper;
import io.onicodes.issue_tracker.repositories.AppUserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder encoder;

    public AppUserDto create(RegistrationRequestDto requestDto) {
        var user = AppUserMapper.INSTANCE.registrationRequestDtoToAppUser(requestDto);
        var encodedPwd = encoder.encode(requestDto.getPassword());
        user.setPassword(encodedPwd);
        var newUser = userRepository.save(user);
        return AppUserMapper.INSTANCE.appUserToAppUserDto(newUser);
    }
}
