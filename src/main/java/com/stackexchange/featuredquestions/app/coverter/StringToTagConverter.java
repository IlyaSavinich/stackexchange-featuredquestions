package com.stackexchange.featuredquestions.app.coverter;

import com.stackexchange.featuredquestions.converter.Converter;
import com.stackexchange.featuredquestions.domain.entity.QuestionTag;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class StringToTagConverter implements Converter<String, QuestionTag> {
    @Override
    public QuestionTag convert(String tagName) {
        val tag = new QuestionTag();
        tag.setName(tagName);
        return tag;
    }
}
