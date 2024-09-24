package com.cassio.nicepay.service;

import static com.cassio.nicepay.entity.Situation.COMPLETED;
import static com.cassio.nicepay.entity.Situation.PENDING;
import static com.cassio.nicepay.entity.UserType.BUSINESS;

import com.cassio.nicepay.client.AuthorizeClient;
import com.cassio.nicepay.entity.Transfer;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.exception.BusinessUserTransferException;
import com.cassio.nicepay.repository.TransferRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

  private final TransferRepository transferRepository;
  private final UserService userService;
  private final AuthorizeClient authorizeClient;

  public TransferService(TransferRepository transferRepository, UserService userService,
      AuthorizeClient authorizeClient) {
    this.transferRepository = transferRepository;
    this.userService = userService;
    this.authorizeClient = authorizeClient;
  }

  @Transactional
  public Transfer transfer(Transfer transfer) {
    authorizeClient.authorize();

    transfer.setSituation(PENDING);
    transferRepository.save(transfer);

    User payer = userService.findUserById(transfer.getPayer());
    validatePayer(payer);

    userService.withdrawal(payer.getId(), transfer.getValue());
    userService.deposit(transfer.getPayee(), transfer.getValue());
    transfer.setSituation(COMPLETED);

    return transferRepository.save(transfer);
  }

  private void validatePayer(User payer) {
    if (payer.getUserType() == BUSINESS) {
      throw new BusinessUserTransferException(payer.getId());
    }
  }

  public List<Transfer> getAll() {
    return transferRepository.findAll();
  }
}
