package com.cassio.nicepay.controller.converter;

import static com.cassio.nicepay.util.DocumentUtil.cleanDocument;

import com.cassio.nicepay.controller.dto.UserResponseDTO;
import com.cassio.nicepay.controller.dto.UserResquesDTO;
import com.cassio.nicepay.entity.User;

public class UserConverter {

  public static User toUser(UserResquesDTO resquesDTO) {
    return new User(
        resquesDTO.getFullName(),
        cleanDocument(resquesDTO.getDocument()),
        resquesDTO.getEmail(),
        resquesDTO.getPassword(),
        resquesDTO.getUserType()
    );
  }

  public static UserResponseDTO toDTO(User user) {
    return new UserResponseDTO(
        user.getId(),
        user.getFullName(),
        user.getDocument(),
        user.getEmail(),
        user.getPassword(),
        user.getUserType()
    );
  }

}
