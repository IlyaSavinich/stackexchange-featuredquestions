package com.stackexchange.featuredquestions.app.listener;

import com.stackexchange.featuredquestions.converter.Converter;
import com.stackexchange.featuredquestions.domain.entity.FeaturedQuestion;
import com.stackexchange.featuredquestions.domain.service.FeaturedQuestionService;
import com.stackexchange.featuredquestions.infra.client.StackExchangeClient;
import com.stackexchange.featuredquestions.infra.client.dto.FeaturedQuestionDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "test.enabled", havingValue = "false")
@AllArgsConstructor
public class ApplicationStartupListener {

    private final StackExchangeClient stackExchangeClient;
    private final FeaturedQuestionService featuredQuestionService;
    private final Converter<FeaturedQuestionDto, FeaturedQuestion> featuredQuestionConverter;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        val featuredQuestions = stackExchangeClient.getLastFeaturedQuestions(20);
        featuredQuestionService.createAll(featuredQuestionConverter.convertAll(featuredQuestions));
    }
}
