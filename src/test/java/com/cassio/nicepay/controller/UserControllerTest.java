package com.cassio.nicepay.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cassio.nicepay.controller.dto.UserRequestDTO;
import com.cassio.nicepay.controller.dto.UserResponseDTO;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.UserType;
import com.cassio.nicepay.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  String uuid = UUID.randomUUID().toString();
  private User user;

  @BeforeEach
  public void setup() {
    user = new User(
        uuid,
        "John Doe",
        "959.895.090-57",
        "john@example.com",
        "password123",
        UserType.PERSONAL
    );
  }

  @Test
  void shouldCreateUserAndStatus201() throws Exception {
    UserRequestDTO request = new UserRequestDTO(
        user.getFullName(),
        user.getDocument(),
        user.getEmail(),
        user.getPassword()
    );

    when(userService.create(ArgumentMatchers.any(User.class))).thenReturn(user);

    mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(uuid)))
        .andExpect(jsonPath("$.fullName", is("John Doe")))
        .andExpect(jsonPath("$.email", is("john@example.com")));
  }

  @Test
  void shouldThrowBadRequestWhenInvalidDocument() throws Exception {
    UserRequestDTO request = new UserRequestDTO(
        user.getFullName(),
        "123456789",
        user.getEmail(),
        user.getPassword()
    );

    mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldThrowBadRequestWhenInvalidEmail() throws Exception {
    UserRequestDTO request = new UserRequestDTO(
        user.getFullName(),
        user.getDocument(),
        "mail#test",
        user.getPassword()
    );

    mockMvc.perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetAllUsers() throws Exception {
    when(userService.getAll()).thenReturn(Collections.singletonList(user));

    mockMvc.perform(get("/user"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(1)))
        .andExpect(jsonPath("$[0].id", is(uuid)))
        .andExpect(jsonPath("$[0].fullName", is("John Doe")));
  }

  @Test
  void testDeposit() throws Exception {
    doNothing().when(userService).deposit("1", new BigDecimal("100.00"));

    mockMvc.perform(MockMvcRequestBuilders.patch("/user/1/deposit/100.00"))
        .andExpect(status().isOk());
  }
}
