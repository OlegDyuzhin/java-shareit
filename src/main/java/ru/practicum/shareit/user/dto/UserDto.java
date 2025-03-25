package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.util.Create;
import ru.practicum.shareit.util.Update;

@Data
@Builder
public class UserDto {
    private Long id;

    @NotBlank(groups = {Create.class}, message = "Имя не может быть пустым")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Электронная почта не может быть пустой")
    @Email(groups = {Create.class, Update.class}, message = "Электронная почта должна содержать символ @")
    private String email;
}
