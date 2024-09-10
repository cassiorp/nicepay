package com.cassio.nicepay.controller.converter;

import static com.cassio.nicepay.entity.UserType.BUSINESS;
import static com.cassio.nicepay.entity.UserType.PERSONAL;
import static com.cassio.nicepay.util.DocumentUtil.cleanDocument;
import static com.cassio.nicepay.util.DocumentUtil.isCPF;

import com.cassio.nicepay.controller.dto.UserResponseDTO;
import com.cassio.nicepay.controller.dto.UserRequestDTO;
import com.cassio.nicepay.entity.User;

public class UserConverter {

  public static User toEntity(UserRequestDTO resquesDTO) {
    String document = cleanDocument(resquesDTO.getDocument());
    return new User(
        resquesDTO.getFullName(),
        document,
        resquesDTO.getEmail(),
        resquesDTO.getPassword(),
        isCPF(document) ? PERSONAL : BUSINESS
    );
  }

  public static UserResponseDTO toDTO(User user) {
    return new UserResponseDTO(
        user.getId(),
        user.getFullName(),
        user.getDocument(),
        user.getEmail(),
        user.getUserType(),
        user.getWallet()
    );
  }

}
