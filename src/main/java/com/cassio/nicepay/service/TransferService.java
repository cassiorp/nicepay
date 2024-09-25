package com.cassio.nicepay.service;

import static com.cassio.nicepay.entity.Situation.COMPLETED;
import static com.cassio.nicepay.entity.Situation.DECLINED;
import static com.cassio.nicepay.entity.Situation.PENDING;
import static com.cassio.nicepay.entity.UserType.BUSINESS;

import com.cassio.nicepay.client.authorize.AuthorizeClient;
import com.cassio.nicepay.client.notify.NotifyClient;
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
  private final NotifyClient notifyClient;

  public TransferService(TransferRepository transferRepository, UserService userService,
      AuthorizeClient authorizeClient, NotifyClient notifyClient) {
    this.transferRepository = transferRepository;
    this.userService = userService;
    this.authorizeClient = authorizeClient;
    this.notifyClient = notifyClient;
  }

  @Transactional
  public Transfer transfer(Transfer transfer) {
    authorizeClient.authorize();

    User payer = userService.findUserById(transfer.getPayer());

    if(isValidPayer(payer)){
      transfer.setSituation(PENDING);
      transferRepository.save(transfer);

      userService.withdrawal(payer.getId(), transfer.getValue());
      userService.deposit(transfer.getPayee(), transfer.getValue());
      transfer.setSituation(COMPLETED);

      transfer = transferRepository.save(transfer);
      notifyClient.notifyTransfer();
      return transfer;
    }else{
      transfer.setSituation(DECLINED);
      transferRepository.save(transfer);
      throw new BusinessUserTransferException(payer.getId());
    }

  }

  private boolean isValidPayer(User payer) {
    return payer.getUserType() != BUSINESS;
  }

  public List<Transfer> getAll() {
    return transferRepository.findAll();
  }
}
