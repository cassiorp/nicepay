package com.cassio.nicepay.service;

import static com.cassio.nicepay.entity.Situation.COMPLETED;
import static com.cassio.nicepay.entity.Situation.PENDING;
import static com.cassio.nicepay.entity.UserType.BUSINESS;

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

  public TransferService(TransferRepository transferRepository, UserService userService) {
    this.transferRepository = transferRepository;
    this.userService = userService;
  }

  @Transactional
  public Transfer transfer(Transfer transfer) {
    transfer.setSituation(PENDING);
    transferRepository.save(transfer);

    User payer = userService.findUserById(transfer.getPayer());
    validatePayer(payer);

    User payee =  userService.findUserById(transfer.getPayee());

    userService.withdrawal(payer, transfer.getValue());
    userService.deposit(payee, transfer.getValue());
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
