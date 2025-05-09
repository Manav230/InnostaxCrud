package com.example.crud.Service;

import com.example.crud.Dao.primary.ClientRepository;
import com.example.crud.Dto.ClientDTO;
import com.example.crud.Model.primary.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(ClientDTO dto) {
        Client client = new Client();
        client.setClientName(dto.getClientName());
        client.setPhoneNo(dto.getPhoneNo());
        client.setEmail(dto.getEmail());
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public void softDeleteClient(final Long id) {
        Client client = clientRepository.getById(id);
        client.setDeleted(true);
        clientRepository.save(client);
    }

    public void restoreClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setDeleted(false);
        clientRepository.save(client);
    }


}
