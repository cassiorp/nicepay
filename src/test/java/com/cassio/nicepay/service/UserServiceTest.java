package com.cassio.nicepay.service;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.UserType;
import com.cassio.nicepay.entity.Wallet;
import com.cassio.nicepay.exception.DocumentAlreadyExistsException;
import com.cassio.nicepay.exception.EmailAlreadyExistsException;
import com.cassio.nicepay.exception.InsufficientBalanceException;
import com.cassio.nicepay.exception.UserNotFoundException;
import com.cassio.nicepay.repository.UserRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @Test
  void shoudlCreateUser() {
    User user = mockUser();
    User userSaved = mockUserWithUuid();

    when(userRepository.existsByDocument(user.getDocument())).thenReturn(false);
    when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
    when(userRepository.save(user)).thenReturn(userSaved);

    User created = userService.create(user);

    assertThat(created.getId(), equalTo(userSaved.getId()));
    assertThat(created.getPassword(), equalTo(userSaved.getPassword()));
    assertThat(created.getWallet(), equalTo(userSaved.getWallet()));
  }

  @Test
  void shoudlThrowDocumentAlreadyExistsException() {
    User user = mockUser();

    when(userRepository.existsByDocument(user.getDocument())).thenReturn(true);

    DocumentAlreadyExistsException throwable = catchThrowableOfType(
        () -> userService.create(user),
        DocumentAlreadyExistsException.class
    );

    assertThat(throwable.getClass(), equalTo(DocumentAlreadyExistsException.class));
    assertThat(throwable.getMessage(), equalTo("Document already exists: " + user.getDocument()));
  }

  @Test
  void shoudlThrowEmailAlreadyExistsException() {
    User user = mockUser();

    when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

    EmailAlreadyExistsException throwable = catchThrowableOfType(
        () -> userService.create(user),
        EmailAlreadyExistsException.class
    );

    assertThat(throwable.getClass(), equalTo(EmailAlreadyExistsException.class));
    assertThat(throwable.getMessage(), equalTo("Email already exists: " + user.getEmail()));
  }

  @Test
  void shouldGetAllUsers() {
    when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser(), mockUserWithUuid()));

    List<User> users = userService.getAll();

    assertThat(users.size(), equalTo(2));
  }

  @Test
  void shouldThrowUserNotFoundException() {
    User user = mockUserWithUuid();
    when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

    UserNotFoundException throwable = catchThrowableOfType(
        () -> userService.findUserById(user.getId()),
        UserNotFoundException.class
    );

    assertThat(throwable.getClass(), equalTo(UserNotFoundException.class));
    assertThat(throwable.getMessage(), equalTo("No User found with " + user.getId()));
  }

  @Test
  void shouldGetUserById() {
    User user = mockUserWithUuid();
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    User userById = userService.findUserById(user.getId());

    assertThat(userById, equalTo(user));
  }

  @Test
  void shouldDeposit() {
    User user = mockUserWithUuid();

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    userService.deposit(user.getId(), BigDecimal.TEN);

    assertThat(new BigDecimal("10"), equalTo(user.getWallet().getBalance()));
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void shouldWithdrawl() {
    User user = mockUserWithUuid();
    user.setWallet(new Wallet(new BigDecimal("100")));

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    userService.withdrawal(user.getId(), new BigDecimal("90"));

    assertThat(new BigDecimal("10"), equalTo(user.getWallet().getBalance()));
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void shouldThrowInsufficientBalanceException() {
    User user = mockUserWithUuid();
    user.setWallet(new Wallet(new BigDecimal("100")));

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    InsufficientBalanceException throwable = catchThrowableOfType(
        () -> userService.withdrawal(user.getId(), new BigDecimal("101")),
        InsufficientBalanceException.class
    );

    assertThat(throwable.getClass(), equalTo(InsufficientBalanceException.class));
    assertThat(throwable.getMessage(), equalTo("Insufficient balance for user: " + user.getId()));
  }

  private User mockUser() {
    return new User(
        "John Doe",
        "959.895.090-57",
        "john@example.com",
        String.valueOf("password123".hashCode()),
        UserType.PERSONAL,
        new Wallet(BigDecimal.ZERO)
    );
  }

  private User mockUserWithUuid() {
    return new User(
        UUID.randomUUID().toString(),
        "John Doe",
        "959.895.090-57",
        "john@example.com",
        String.valueOf("password123".hashCode()),
        UserType.PERSONAL,
        new Wallet(BigDecimal.ZERO)
    );
  }
}
