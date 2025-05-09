package com.example.crud.Service;

import com.example.crud.Dao.primary.ClientRepository;
import com.example.crud.Dao.primary.PaymentRequestRepository;
import com.example.crud.Dto.PaymentRequestDTO;
import com.example.crud.Model.primary.Client;
import com.example.crud.Model.primary.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentRequestService {

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public PaymentRequest createPaymentRequest(PaymentRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        PaymentRequest pr = new PaymentRequest();
        pr.setCurrency(dto.getCurrency());
        pr.setAmount(dto.getAmount());
        pr.setClient(client);

        return paymentRequestRepository.save(pr);
    }

    public List <PaymentRequest> getAll() {
        return paymentRequestRepository.findAll();
    }

    public Optional<PaymentRequest> getById(Long id) {
        return paymentRequestRepository.findById(id);
    }
}
