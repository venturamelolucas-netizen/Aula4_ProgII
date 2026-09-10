package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Item;
import com.example.demo.repositories.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item findById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElseThrow(() -> new RuntimeException(
                "Item não encontrado! Id: " + id + ", Tipo: " + Item.class.getName()));
    }

    @Transactional
    public Item create(Item obj) {
        obj.setId(null);
        return itemRepository.save(obj);
    }

    @Transactional
    public Item update(Item obj) {
        Item newObj = findById(obj.getId());
        newObj.setDescricao(obj.getDescricao());
        return itemRepository.save(newObj);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            itemRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!", e);
        }
    }
}