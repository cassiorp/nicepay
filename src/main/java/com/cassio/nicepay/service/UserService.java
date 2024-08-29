package com.cassio.nicepay.service;

import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.Wallet;
import com.cassio.nicepay.exception.BusinessException;
import com.cassio.nicepay.exception.DocumentAlreadyExistsException;
import com.cassio.nicepay.exception.EmailAlreadyExistsException;
import com.cassio.nicepay.exception.UserNotFoundException;
import com.cassio.nicepay.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public User create(User user) {
    validateUniqueFields(user);

    try {
      user.setWallet(new Wallet(BigDecimal.ZERO));
      return userRepository.save(user);
    } catch (RuntimeException e) {
      logger.error("Error creating user: {}", e.getMessage(), e);
      throw new BusinessException();
    }
  }

  private void validateUniqueFields(User user) {
    if (userRepository.existsByDocument(user.getDocument())) {
      throw new DocumentAlreadyExistsException(user.getDocument());
    }
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new EmailAlreadyExistsException(user.getEmail());
    }
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Transactional
  public void deposit(String userId, BigDecimal amount) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    Wallet wallet = user.getWallet();
    wallet.setBalance(wallet.getBalance().add(amount));

    try {
      userRepository.save(user);
    } catch (RuntimeException e) {
      logger.error("Error depositing amount: {}", e.getMessage(), e);
      throw new BusinessException();
    }
  }
}
