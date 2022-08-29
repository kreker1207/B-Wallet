package software.sigma.sip.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.sip.application.service.UserService;
import software.sigma.sip.infrastructure.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority(T(software.sigma.sip.domain.entity.Permission).DEVELOPER_READ)")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers().stream().map(UserDto::toUserDto).toList();
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(software.sigma.sip.domain.entity.Permission).DEVELOPER_WRITE)")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto.toUser());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable Long id) {
        return UserDto.toUserDto(userService.getUser(id));
    }

    @PutMapping
    @PreAuthorize("hasAuthority(T(software.sigma.sip.domain.entity.Permission).DEVELOPER_WRITE)")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto.toUser());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
    }
}
