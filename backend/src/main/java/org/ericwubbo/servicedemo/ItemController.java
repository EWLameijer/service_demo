package org.ericwubbo.servicedemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("${codemo.cors}")
public class ItemController {
    private final ItemRepository itemRepository;

    private final ItemService itemService;

    @GetMapping
    public Iterable<Item> getAll() {
        return itemRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Item> getById(@PathVariable long id) {
        return itemRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item, UriComponentsBuilder uriComponentsBuilder) {
        if (item.isValid() || item.getId() != null) return ResponseEntity.badRequest().build();
        itemRepository.save(item);
        var location = uriComponentsBuilder.path("{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(location).body(item);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) return ResponseEntity.notFound().build();
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item itemUpdates) {
        if (itemUpdates.getId() != null) return ResponseEntity.badRequest().build();
        if (itemService.isInvalidPatch(itemUpdates)) return ResponseEntity.badRequest().build();

        Optional<Item> possibleItem = itemRepository.findById(id);
        if (possibleItem.isEmpty()) return ResponseEntity.notFound().build();
        Item item = possibleItem.get();
        itemService.patchWith(item, itemUpdates);
        itemRepository.save(item);
        return ResponseEntity.ok(item);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Item updatedItem) {
        if (updatedItem.getId() == null) return ResponseEntity.badRequest().build();
        if (!updatedItem.isValid()) return ResponseEntity.badRequest().build();
        Optional<Item> possibleItem = itemRepository.findById(updatedItem.getId());
        if (possibleItem.isEmpty()) return ResponseEntity.notFound().build();
        itemRepository.save(updatedItem);
        return ResponseEntity.noContent().build();
    }
}
