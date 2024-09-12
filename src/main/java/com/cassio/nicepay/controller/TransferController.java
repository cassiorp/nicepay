package com.cassio.nicepay.controller;

import static com.cassio.nicepay.controller.converter.TransferConverter.toDTO;
import static com.cassio.nicepay.controller.converter.TransferConverter.toEntity;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.cassio.nicepay.controller.converter.TransferConverter;
import com.cassio.nicepay.controller.dto.TransferRequestDTO;
import com.cassio.nicepay.controller.dto.TransferResponseDTO;
import com.cassio.nicepay.entity.Transfer;
import com.cassio.nicepay.service.TransferService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

  private final TransferService transferService;

  public TransferController(TransferService transferService) {
    this.transferService = transferService;
  }

  @PostMapping
  public ResponseEntity<TransferResponseDTO> transfer(
      @RequestBody @Validated TransferRequestDTO transferRequestDTO
  ) {
    Transfer transfer = transferService.transfer(toEntity(transferRequestDTO));
    return new ResponseEntity<>(toDTO(transfer), CREATED);
  }

  @GetMapping
  public ResponseEntity<List<TransferResponseDTO>> getAll() {
    List<TransferResponseDTO> transfers = transferService.getAll()
        .stream().map(TransferConverter::toDTO)
        .toList();
    return new ResponseEntity<>(transfers, OK);
  }
}
