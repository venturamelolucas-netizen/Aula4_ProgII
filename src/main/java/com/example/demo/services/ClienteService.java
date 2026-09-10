package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Cliente;
import com.example.demo.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new RuntimeException(
                "Cliente não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente create(Cliente obj) {
        obj.setId(null);
        return clienteRepository.save(obj);
    }

    @Transactional
    public Cliente update(Cliente obj) {
        Cliente newObj = findById(obj.getId());
        newObj.setNome(obj.getNome());
        return clienteRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!", e);
        }
    }
}