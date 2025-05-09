package com.example.crud.Controller;

import com.example.crud.Dto.VaultDTO;
import com.example.crud.Model.secondary.Vault;
import com.example.crud.Service.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vault")
public class VaultController {
    @Autowired
    private VaultService vaultService;

    @PostMapping
    public Vault createVault(@RequestBody VaultDTO vaultDTO) {
        return vaultService.saveVault(vaultDTO);
    }

    @GetMapping("/{id}")
    public Vault getVaultById(@PathVariable Long id) {
        return vaultService.getVaultById(id);
    }
}
