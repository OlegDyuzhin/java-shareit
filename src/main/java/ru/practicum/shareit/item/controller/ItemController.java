package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.util.Create;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;
    private static final String USER_HEADER = "X-Sharer-User-Id";

    @GetMapping
    public List<ItemDto> getItemsByUserId(@RequestHeader(USER_HEADER) Long userId) {
        log.info("Запрос предметов для пользователя {}", userId);
        return itemService.getItemsByUserId(userId);
    }

    @GetMapping("/{id}")
    public ItemDto get(@PathVariable Long id) {
        log.info("Запрос предмета {}", id);
        return itemService.getItem(id);
    }

    @PostMapping
    public ItemDto create(@RequestHeader(USER_HEADER) Long userId,
                          @Validated(Create.class) @RequestBody ItemDto itemDto) {
        log.info("Создание предмета {} для юзера id {}", itemDto, userId);
        userService.getUser(userId);
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestBody ItemDto itemDto, @PathVariable Long id,
                          @RequestHeader(USER_HEADER) Long userId) {
        log.info("Обновление предмета {} c id {} для пользователя {}", itemDto, id, userId);
        userService.getUser(userId);
        return itemService.update(itemDto, id, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }
}