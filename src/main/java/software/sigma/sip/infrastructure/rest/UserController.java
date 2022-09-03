package software.sigma.sip.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/users")
@Tag(name = "Users", description = "CRUD operations for user resources")
public class UserController {
    private final UserService userService;

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   @Operation(summary = "Get all users", responses = {
           @ApiResponse(responseCode = "200", description = "Found users",
                   content = {
                           @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
                   }),
           @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
           @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
   })
   public List<UserDto> getUsers() {
        return userService.getUsers().stream().map(UserDto::toUserDto).toList();
    }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   @Operation(summary = "Create user", responses = {
           @ApiResponse(responseCode = "201", description = "User was created")
   })
   public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto.toUser());
    }

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.OK)
   @Operation(summary = "Get user by id", responses = {
           @ApiResponse(responseCode = "200", description = "Found user",
                   content = {
                           @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                   }),
           @ApiResponse(responseCode = "404", description = "User was not found by id", content = @Content),
           @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
           @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
   })
   public UserDto getUser(@PathVariable Long id) {
        return UserDto.toUserDto(userService.getUser(id));
    }

   @PutMapping
   @ResponseStatus(HttpStatus.OK)
   @Operation(summary = "Update user", responses = {
           @ApiResponse(responseCode = "201", description = "User was updated"),
           @ApiResponse(responseCode = "404", description = "User was not found by id"),
           @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
           @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
   })
   public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto.toUser());
    }

   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Operation(summary = "Deactivate user by id", responses = {
           @ApiResponse(responseCode = "204", description = "User was deactivated"),
           @ApiResponse(responseCode = "404", description = "User was not found by id"),
           @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
           @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
   })
   public void deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
    }
}
