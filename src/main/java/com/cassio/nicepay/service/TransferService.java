package com.cassio.nicepay.service;

import static com.cassio.nicepay.entity.Situation.COMPLETED;
import static com.cassio.nicepay.entity.Situation.DECLINED;
import static com.cassio.nicepay.entity.Situation.PENDING;
import static com.cassio.nicepay.entity.UserType.BUSINESS;

import com.cassio.nicepay.client.authorize.AuthorizeClient;
import com.cassio.nicepay.client.notify.NotifyClient;
import com.cassio.nicepay.entity.Transfer;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.exception.BusinessException;
import com.cassio.nicepay.exception.BusinessUserTransferException;
import com.cassio.nicepay.repository.TransferRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
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
    logger.debug("Starting transfer: {}", transfer);

    authorizeClient.authorize();

    User payer = userService.findUserById(transfer.getPayer());

    transfer = preProcess(transfer);

    if (isValidPayer(payer)) {
      transfer = processTransfer(transfer, payer);
      notifyClient.notifyTransfer();
      logger.debug("transfer processed: {}", transfer);
    } else {
      transfer = declineTransfer(transfer, payer);
      logger.error("transfer declined: {}", transfer);
      throw new BusinessUserTransferException(payer.getId());
    }

    return transfer;
  }

  private Transfer preProcess(Transfer transfer) {
    transfer.setSituation(PENDING);
    return transferRepository.save(transfer);
  }

  private Transfer declineTransfer(Transfer transfer, User payer) {
    transfer.setSituation(DECLINED);
    return transferRepository.save(transfer);
  }

  private Transfer processTransfer(Transfer transfer, User payer) {
    userService.withdrawal(payer.getId(), transfer.getValue());
    userService.deposit(transfer.getPayee(), transfer.getValue());
    transfer.setSituation(COMPLETED);
    transfer = transferRepository.save(transfer);
    return transfer;
  }

  public List<Transfer> getAll() {
    return transferRepository.findAll();
  }

  private boolean isValidPayer(User payer) {
    return payer.getUserType() != BUSINESS;
  }

  private Transfer save(Transfer transfer) {
    try {
      return transferRepository.save(transfer);
    } catch (RuntimeException e) {
      logger.error("Error saving transfer: {}", e.getMessage());
      throw new BusinessException();
    }
  }
}
