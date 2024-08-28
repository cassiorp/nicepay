package com.cassio.nicepay.controller;

import static com.cassio.nicepay.controller.converter.UserConverter.toDTO;
import static com.cassio.nicepay.controller.converter.UserConverter.toUser;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.cassio.nicepay.controller.converter.UserConverter;
import com.cassio.nicepay.controller.dto.UserResponseDTO;
import com.cassio.nicepay.controller.dto.UserResquesDTO;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> create(
      @RequestBody @Validated UserResquesDTO userResquesDTO
  ) {
    User user = userService.create(toUser(userResquesDTO));
    return new ResponseEntity<>(toDTO(user), CREATED);
  }

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAll() {
    List<UserResponseDTO> users = userService.getAll().stream().map(UserConverter::toDTO).toList();
    return new ResponseEntity<>(users, OK);
  }

  @PatchMapping("/{userId}/deposit/{amount}")
  public void deposit(@PathVariable String userId, @PathVariable BigDecimal amount) {
    userService.deposit(userId, amount);
  }
}
