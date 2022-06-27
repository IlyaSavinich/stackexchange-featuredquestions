package com.stackexchange.featuredquestions.app.controller;

import com.stackexchange.featuredquestions.app.listener.ApplicationStartupListener;
import com.stackexchange.featuredquestions.converter.Converter;
import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.service.FeaturedQuestionService;
import com.stackexchange.featuredquestions.infra.client.StackExchangeClient;
import com.stackexchange.featuredquestions.infra.client.dto.FeaturedQuestionDto;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public abstract class BaseHttpIT {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private StackExchangeClient stackExchangeClient;

    @Autowired
    private FeaturedQuestionService featuredQuestionService;

    @Autowired
    private Converter<FeaturedQuestionDto, FeaturedQuestion> featuredQuestionConverter;

    protected MockMvc mockMvc;

    @SneakyThrows
    protected static String readResource(String path) {
        return Files.readString(Paths.get(Objects.requireNonNull(BaseHttpIT.class.getResource(path)).toURI()));
    }

    @BeforeAll
    public final void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        setupStubs();
        val listener = new ApplicationStartupListener(
                stackExchangeClient,
                featuredQuestionService,
                featuredQuestionConverter
        );
        listener.onApplicationEvent(null);
    }

    protected void setupStubs() {

    };
}
