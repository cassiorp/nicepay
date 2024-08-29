package com.cassio.nicepay.service;

import static com.cassio.nicepay.entity.Situation.EFFECTED;
import static com.cassio.nicepay.entity.Situation.REFUSED;
import static com.cassio.nicepay.entity.UserType.BUSINESS;

import com.cassio.nicepay.entity.Transfer;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.exception.BusinessUserTransferException;
import com.cassio.nicepay.exception.InsufficientBalanceException;
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
    transfer.setSituation(REFUSED);
    transferRepository.save(transfer);

    User payer = userService.findUserById(transfer.getPayer());

    if (payer.getUserType().equals(BUSINESS)) {
      throw new BusinessUserTransferException(payer.getId());
    }

    if (transfer.getValue().compareTo(payer.getWallet().getBalance()) > -1) {
      throw new InsufficientBalanceException(payer.getId());
    }

    User payee = userService.findUserById(transfer.getPayee());

    userService.withdrawal(payer, transfer.getValue());
    userService.deposit(payee, transfer.getValue());
    transfer.setSituation(EFFECTED);
    return transferRepository.save(transfer);
  }

  public List<Transfer> getAll() {
    return transferRepository.findAll();
  }
}
