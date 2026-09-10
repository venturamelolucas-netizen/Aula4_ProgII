package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Pedido;
import com.example.demo.repositories.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public Pedido findById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new RuntimeException(
                "Pedido não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public List<Pedido> findAllByCliente_Id(Long id) {
        clienteService.findById(id);
        return pedidoRepository.findByCliente_Id(id);
    }

    @Transactional
    public Pedido create(Pedido obj) {
        obj.setId(null);
        obj.setCliente(clienteService.findById(obj.getCliente().getId()));
        return pedidoRepository.save(obj);
    }

    @Transactional
    public Pedido update(Pedido obj) {
        Pedido newObj = findById(obj.getId());
        newObj.setCliente(clienteService.findById(obj.getCliente().getId()));
        return pedidoRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            pedidoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!", e);
        }
    }
}