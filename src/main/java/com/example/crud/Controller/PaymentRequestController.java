package com.example.crud.Controller;

import com.example.crud.Dto.PaymentRequestDTO;
import com.example.crud.Model.primary.PaymentRequest;
import com.example.crud.Service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentRequestController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @PostMapping
    public ResponseEntity<PaymentRequest> create(@RequestBody PaymentRequestDTO dto) {
        PaymentRequest saved = paymentRequestService.createPaymentRequest(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PaymentRequest> getAll() {
        return paymentRequestService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<PaymentRequest> getById(@PathVariable Long id) {
        return paymentRequestService.getById(id);
    }

}
