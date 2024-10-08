package com.cassio.nicepay.service;

import static com.cassio.nicepay.entity.Situation.COMPLETED;
import static com.cassio.nicepay.entity.Situation.DECLINED;
import static com.cassio.nicepay.entity.Situation.PENDING;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import com.cassio.nicepay.client.authorize.AuthorizeClient;
import com.cassio.nicepay.client.notify.NotifyClient;
import com.cassio.nicepay.entity.Transfer;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.UserType;
import com.cassio.nicepay.entity.Wallet;
import com.cassio.nicepay.exception.BusinessUserTransferException;
import com.cassio.nicepay.exception.ForbiddenException;
import com.cassio.nicepay.repository.TransferRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

  @Mock
  private UserService userService;
  @Mock
  private TransferRepository transferRepository;
  @Mock
  private AuthorizeClient authorizeClient;
  @Mock
  private NotifyClient notifyClient;
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
    when(authorizeClient.authorize()).thenReturn(new ResponseEntity<>(OK));

    Transfer transferCompleted = transferService.transfer(transfer);

    assertThat(transferCompleted.getSituation(), equalTo(COMPLETED));
    verify(transferRepository, times(2)).save(any());
    verify(userService, times(1)).withdrawal(payer.getId(), transfer.getValue());
    verify(userService, times(1)).deposit(payee.getId(), transfer.getValue());
    verify(notifyClient, times(1)).notifyTransfer();
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
    when(authorizeClient.authorize()).thenReturn(new ResponseEntity<>(OK));

    BusinessUserTransferException throwable = catchThrowableOfType(
        () -> transferService.transfer(transfer),
        BusinessUserTransferException.class
    );

    assertThat(throwable.getClass(), equalTo(BusinessUserTransferException.class));
    assertThat(throwable.getMessage(), equalTo("Business user cannot transfer: " + payer.getId()));
    assertThat(transfer.getSituation(), equalTo(DECLINED));
  }

  @Test
  void shouldThrowForbiddenException() {
    User payer = mockPayerBusiness();
    User payee = mockPayee();

    Transfer transfer = new Transfer(
        BigDecimal.TEN,
        payer.getId(),
        payee.getId()
    );

    when(authorizeClient.authorize()).thenThrow(new ForbiddenException());

    ForbiddenException throwable = catchThrowableOfType(
        () -> transferService.transfer(transfer),
        ForbiddenException.class
    );

    assertThat(throwable.getClass(), equalTo(ForbiddenException.class));
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
