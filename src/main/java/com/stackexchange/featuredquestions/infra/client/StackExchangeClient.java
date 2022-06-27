package com.stackexchange.featuredquestions.infra.client;

import com.stackexchange.featuredquestions.infra.client.dto.*;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Component
public class StackExchangeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackExchangeClient.class);

    private final RestOperations restOps;
    private final String featuredQuestionsEndpoint;
    private final String usersEndpoint;

    public StackExchangeClient(
        RestOperations restOps,
        @Value("${stackExchange.url}${stackExchange.questions}")
        String featuredQuestionsEndpoint,
        @Value("${stackExchange.url}${stackExchange.users}")
        String usersEndpoint
    ) {
        this.restOps = restOps;
        this.featuredQuestionsEndpoint = featuredQuestionsEndpoint;
        this.usersEndpoint = usersEndpoint;
    }

    public Collection<FeaturedQuestionDto> getLastFeaturedQuestions(Integer count) {
        val requestParams = new HashMap<String, Object>();
        requestParams.put("pagesize", count);
        requestParams.put("order", "desc");
        requestParams.put("sort", "creation");
        requestParams.put("site", "stackoverflow");
        val questions = restOps.getForEntity(featuredQuestionsEndpoint, FeaturedQuestionPage.class, requestParams);
        return Objects.requireNonNull(questions.getBody()).getItems();
    }

    @Cacheable({"users"})
    public UserResponseDto getUserById(Integer userId) {
        LOGGER.info("Calling {}", usersEndpoint);
        val requestParams = new HashMap<String, Object>();
        requestParams.put("site", "stackoverflow");
        requestParams.put("id", userId);
        val result = Objects.requireNonNull(
                restOps.getForEntity(usersEndpoint, UserPage.class, requestParams).getBody()
        );
        if (result.getItems().isEmpty()) throw new EntityNotFoundException("User with id '" + userId + "' wasn't found");
        return result.getItems().get(0);
    }
}
