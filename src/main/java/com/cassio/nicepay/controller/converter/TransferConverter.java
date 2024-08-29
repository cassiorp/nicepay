package com.cassio.nicepay.controller.converter;

import com.cassio.nicepay.controller.dto.TransferRequestDTO;
import com.cassio.nicepay.controller.dto.TransferResponseDTO;
import com.cassio.nicepay.entity.Transfer;

public class TransferConverter {

  public static Transfer toEntity(TransferRequestDTO transferRequestDTO) {
    return new Transfer(
        transferRequestDTO.getValue(),
        transferRequestDTO.getPayer(),
        transferRequestDTO.getPayee()
    );
  }

  public static TransferResponseDTO toDTO(Transfer transfer) {
    return new TransferResponseDTO(
        transfer.getId(),
        transfer.getValue(),
        transfer.getPayer(),
        transfer.getPayee(),
        transfer.getSituation()
    );
  }
}
