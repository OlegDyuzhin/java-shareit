package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;

import java.util.List;

import static ru.practicum.shareit.item.dto.ItemMapper.toItem;
import static ru.practicum.shareit.item.dto.ItemMapper.toItemDto;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepositoryImpl itemRepository;

    public List<ItemDto> getItemsByUserId(Long userId) {
        return itemRepository.getItemsByUserId(userId).stream().map(ItemMapper::toItemDto).toList();
    }

    public ItemDto getItem(Long id) {
        return toItemDto(itemRepository.getItem(id));
    }

    public ItemDto create(ItemDto itemDto, Long userId) {
        return toItemDto(itemRepository.createItem(toItem(itemDto, userId)));
    }

    public ItemDto update(ItemDto itemDto, Long id, Long userId) {
        return toItemDto(itemRepository.update(id, itemDto, userId));
    }

    public void delete(Long id) {
        itemRepository.delete(id);
    }

    public List<ItemDto> search(String text) {
        return itemRepository.search(text).stream().map(ItemMapper::toItemDto).toList();
    }


}
