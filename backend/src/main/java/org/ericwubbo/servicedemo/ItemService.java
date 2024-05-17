package org.ericwubbo.servicedemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public boolean isInvalidPatch(Item itemUpdates) {
        if (itemUpdates.getName() != null && (!itemUpdates.hasValidName() || itemUpdates.getName().length() < 2))
            return true;
        return itemUpdates.getPrice() != null && !itemUpdates.hasValidPrice();
    }

    public void patchWith(Item item, Item updates) {
        if (isInvalidPatch(updates)) throw new IllegalArgumentException("Neglected to use isInvalidPatch-check!");
        String newName = updates.getName();
        if (newName != null) item.setName(newName);
        BigDecimal newPrice = updates.getPrice();
        if (newPrice != null) item.setPrice(newPrice);
    }
}
