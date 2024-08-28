package com.cassio.nicepay.service;

import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.Wallet;
import com.cassio.nicepay.exception.DocumentAlreadyExistsException;
import com.cassio.nicepay.exception.EmailAlreadyExistsException;
import com.cassio.nicepay.exception.UserNotFoundException;
import com.cassio.nicepay.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(User user) {
    if (userRepository.existsByDocument(user.getDocument())) {
      throw new DocumentAlreadyExistsException(user.getDocument());
    }
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new EmailAlreadyExistsException(user.getEmail());
    }
    user.setWallet(new Wallet(new BigDecimal(0)));
    return userRepository.save(user);
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public void deposit(String userId, BigDecimal amount) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    Wallet wallet = user.getWallet();
    BigDecimal newBalance = wallet.getBalance().add(amount);
    wallet.setBalance(newBalance);
    user.setWallet(wallet);
    userRepository.save(user);
  }
}
