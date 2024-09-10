package com.cassio.nicepay.service;

import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.Wallet;
import com.cassio.nicepay.exception.BusinessException;
import com.cassio.nicepay.exception.DocumentAlreadyExistsException;
import com.cassio.nicepay.exception.EmailAlreadyExistsException;
import com.cassio.nicepay.exception.InsufficientBalanceException;
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
    user.setPassword(hashPassword(user.getPassword()));
    user.setWallet(new Wallet(BigDecimal.ZERO));
    return save(user);
  }

  private static String hashPassword(String password) {
    return String.valueOf(password.hashCode());
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
    User user = findUserById(userId);
    Wallet wallet = user.getWallet();
    wallet.setBalance(wallet.getBalance().add(amount));
    save(user);
  }

  @Transactional
  public void deposit(User user, BigDecimal amount) {
    Wallet wallet = user.getWallet();
    wallet.setBalance(wallet.getBalance().add(amount));
    save(user);
  }

  @Transactional
  public void withdrawal(User user, BigDecimal amount) {
    validateBalance(user, amount);
    Wallet wallet = user.getWallet();
    wallet.setBalance(wallet.getBalance().subtract(amount));
    save(user);
  }

  private static void validateBalance(User user, BigDecimal amount) {
    if (amount.compareTo(user.getWallet().getBalance()) > 0) {
      throw new InsufficientBalanceException(user.getId());
    }
  }

  public User findUserById(String id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  public User save(User user) {
    try {
      return userRepository.save(user);
    } catch (RuntimeException e) {
      logger.error("Error saving user: {}", e.getMessage());
      throw new BusinessException();
    }
  }
}
