package com.stackexchange.featuredquestions.app.controller;

import com.stackexchange.featuredquestions.infra.client.StackExchangeClient;
import com.stackexchange.featuredquestions.infra.client.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private final StackExchangeClient stackExchangeClient;

    @GetMapping(path = "/{id}")
    public UserResponseDto getUserById(@PathVariable Integer id) {
        return stackExchangeClient.getUserById(id);
    }

}
