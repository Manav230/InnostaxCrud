package com.example.crud.Service;

import com.example.crud.Dao.primary.PaymentRequestRepository;
import com.example.crud.Dao.secondary.VaultRepository;
import com.example.crud.Dto.VaultDTO;
import com.example.crud.Model.primary.PaymentRequest;
import com.example.crud.Model.secondary.Vault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaultService {

    @Autowired
    private VaultRepository vaultRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    public Vault saveVault(VaultDTO vaultDTO) {
        // Validate that PaymentRequest exists in primary DB
        if (!paymentRequestRepository.existsById(vaultDTO.getReqId())) {
            throw new RuntimeException("PaymentRequest not found for id: " + vaultDTO.getReqId());
        }

        Vault vault = new Vault();
        vault.setCardNo(vaultDTO.getCardNo());
        vault.setCvv(vaultDTO.getCvv());
        vault.setReqId(vaultDTO.getReqId());

        return vaultRepository.save(vault);
    }

    public Vault getVaultById(Long id) {
        return vaultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vault not found for id: " + id));
    }
}
