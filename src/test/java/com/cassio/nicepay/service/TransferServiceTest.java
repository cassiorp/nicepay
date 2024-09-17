package com.cassio.nicepay.service;

import static com.cassio.nicepay.entity.Situation.COMPLETED;
import static com.cassio.nicepay.entity.Situation.PENDING;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cassio.nicepay.entity.Situation;
import com.cassio.nicepay.entity.Transfer;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.UserType;
import com.cassio.nicepay.entity.Wallet;
import com.cassio.nicepay.exception.BusinessUserTransferException;
import com.cassio.nicepay.exception.InsufficientBalanceException;
import com.cassio.nicepay.repository.TransferRepository;
import com.cassio.nicepay.repository.UserRepository;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

  @Mock
  private UserService userService;
  @Mock
  private TransferRepository transferRepository;
  @InjectMocks
  private TransferService transferService;


  @Test
  void shouldTransfer() {
    User payer = mockPayer();
    User payee = mockPayee();

    Transfer transfer = new Transfer(
        BigDecimal.TEN,
        payer.getId(),
        payee.getId()
    );

    when(transferRepository.save(transfer)).thenReturn(transfer);
    when(userService.findUserById(transfer.getPayer())).thenReturn(payer);

    Transfer transferCompleted = transferService.transfer(transfer);

    assertThat(transferCompleted.getSituation(), equalTo(COMPLETED));
    verify(transferRepository, times(2)).save(any());
    verify(userService, times(1)).withdrawal(payer.getId(), transfer.getValue());
    verify(userService, times(1)).deposit(payee.getId(), transfer.getValue());
  }

  @Test
  void shouldThrowBusinessUserTransferExceptionWhenUserBusinessTryToTransfer() {
    User payer = mockPayerBusiness();
    User payee = mockPayee();

    Transfer transfer = new Transfer(
        BigDecimal.TEN,
        payer.getId(),
        payee.getId()
    );

    when(transferRepository.save(transfer)).thenReturn(transfer);
    when(userService.findUserById(transfer.getPayer())).thenReturn(payer);

    BusinessUserTransferException throwable = catchThrowableOfType(
        () -> transferService.transfer(transfer),
        BusinessUserTransferException.class
    );

    assertThat(throwable.getClass(), equalTo(BusinessUserTransferException.class));
    assertThat(throwable.getMessage(), equalTo("Business user cannot transfer: " + payer.getId()));
    assertThat(transfer.getSituation(), equalTo(PENDING));
  }


  @Test
  void shoudlGetAllTransfers() {
    Transfer transfer = new Transfer(
        BigDecimal.TEN,
        mockPayer().getId(),
        mockPayee().getId()
    );
    when(transferRepository.findAll()).thenReturn(Arrays.asList(transfer));

    List<Transfer> transfers = transferService.getAll();

    assertThat(transfers.contains(transfer), equalTo(true));
  }

  private User mockPayer() {
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

  private User mockPayerBusiness() {
    return new User(
        UUID.randomUUID().toString(),
        "John Doe",
        "959.895.090-57",
        "john@example.com",
        String.valueOf("password123".hashCode()),
        UserType.BUSINESS,
        new Wallet(BigDecimal.ZERO)
    );
  }

  private User mockPayee() {
    return new User(
        UUID.randomUUID().toString(),
        "Roberto",
        "333.999.777-66",
        "robertinho.com",
        String.valueOf("password123".hashCode()),
        UserType.PERSONAL,
        new Wallet(BigDecimal.ZERO)
    );
  }


}
