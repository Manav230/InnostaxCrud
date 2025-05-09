package com.example.crud.Controller;

import com.example.crud.Dto.ClientDTO;
import com.example.crud.Model.primary.Client;
import com.example.crud.Service.ClientService;
import com.example.crud.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;



    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.createClient(dto));
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.findAll();
    }

    @GetMapping("/client/{id}")
    public Optional<Client> getById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<?> softDeleteClient(@PathVariable Long id) {
        clientService.softDeleteClient(id);
        return ResponseEntity.ok("Client soft deleted");
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreClient(@PathVariable Long id) {
        clientService.restoreClient(id);
        return ResponseEntity.ok("Client restored successfully");
    }
}
