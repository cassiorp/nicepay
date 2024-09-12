package com.cassio.nicepay.controller;


import static com.cassio.nicepay.entity.Situation.COMPLETED;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cassio.nicepay.controller.dto.TransferRequestDTO;
import com.cassio.nicepay.controller.dto.UserRequestDTO;
import com.cassio.nicepay.entity.Situation;
import com.cassio.nicepay.entity.Transfer;
import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.service.TransferService;
import com.cassio.nicepay.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TransferControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransferService transferService;

  @Autowired
  private ObjectMapper objectMapper;

  private String payerUuid = UUID.randomUUID().toString();
  private String payeeUuid = UUID.randomUUID().toString();
  private BigDecimal value =  new BigDecimal("10");

  @Test
  void shouldTranferAndStatus201() throws Exception {
    Transfer transfer = mockTransfer();
    TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
    transferRequestDTO.setPayee(payeeUuid);
    transferRequestDTO.setPayer(payerUuid);
    transferRequestDTO.setValue(value);

    when(transferService.transfer(ArgumentMatchers.any(Transfer.class))).thenReturn(transfer);

    mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferRequestDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(transfer.getId())))
        .andExpect(jsonPath("$.payer", is(payerUuid)))
        .andExpect(jsonPath("$.payee", is(payeeUuid)));
  }

  @Test
  void shouldThrowBadRequestWhenValueIsNull201() throws Exception {
    TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
    transferRequestDTO.setValue(value);

    mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferRequestDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldThrowBadRequestWhenPayerIsBlank201() throws Exception {
    TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
    transferRequestDTO.setValue(value);
    transferRequestDTO.setPayee(payeeUuid);

    mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferRequestDTO)))
        .andExpect(status().isBadRequest());

    transferRequestDTO.setPayer("");

    mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferRequestDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldThrowBadRequestWhenPayeeIsBlank201() throws Exception {
    TransferRequestDTO transferRequestDTO = new TransferRequestDTO();

    mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferRequestDTO)))
        .andExpect(status().isBadRequest());

    transferRequestDTO.setPayee("");

    mockMvc.perform(MockMvcRequestBuilders.post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transferRequestDTO)))
        .andExpect(status().isBadRequest());
  }


  private Transfer mockTransfer() {
    return new Transfer(
        UUID.randomUUID().toString(),
        value,
        payerUuid,
        payeeUuid,
        COMPLETED
    );
  }

}
