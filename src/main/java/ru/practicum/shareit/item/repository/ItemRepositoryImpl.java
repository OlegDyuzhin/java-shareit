package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotAccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ItemRepositoryImpl {

    private final Map<Long, Item> itemRepository = new HashMap<>();
    private Long id = 0L;

    public List<Item> getItems() {
        return new ArrayList<>(itemRepository.values());
    }

    public List<Item> getItemsByUserId(Long id) {
        return itemRepository.values().stream()
                .filter(item -> item.getOwner().equals(id))
                .toList();
    }

    public Item getItem(Long id) {
        Item item = itemRepository.get(id);
        if (item == null) {
            throw new NotFoundException(String.format("Предмет с id: %d не существует", id));
        }
        return item;
    }

    public Item createItem(Item item) {
        item.setId(++id);
        itemRepository.put(id, item);
        log.info("Предмет создан {}", item);
        return item;
    }

    public Item update(Long id, ItemDto itemDto, Long userId) {
        if (!itemRepository.containsKey(id)) {
            throw new NotFoundException("Вещи с id: " + id + " нет");
        }
        if (!itemRepository.get(id).getOwner().equals(userId)) {
            throw new NotAccessException("Пользователь не владелец предмета");
        }

        Item item = itemRepository.get(id);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        itemRepository.put(itemDto.getId(), item);
        log.info("Предмет успешно обновлен {}", item);
        return item;
    }

    public void delete(Long id) {
        itemRepository.remove(id);
    }

    public List<Item> search(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return itemRepository.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .toList();
    }
}

