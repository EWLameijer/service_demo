package org.ericwubbo.servicedemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;


    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }
}
