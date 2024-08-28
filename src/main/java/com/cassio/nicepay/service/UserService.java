package com.cassio.nicepay.service;

import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.exception.DocumentAlreadyExistsException;
import com.cassio.nicepay.exception.EmailAlreadyExistsException;
import com.cassio.nicepay.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(User user) {
    if(userRepository.existsByDocument(user.getDocument())){
      throw new DocumentAlreadyExistsException(user.getDocument());
    }
    if(userRepository.existsByEmail(user.getEmail())){
      throw new EmailAlreadyExistsException(user.getEmail());
    }
    return userRepository.save(user);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

}
