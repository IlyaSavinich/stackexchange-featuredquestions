package com.stackexchange.featuredquestions.app.coverter;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringToTagConverterTest {

    public StringToTagConverter converter = new StringToTagConverter();

    @Test
    public void testConvert() {
        val tagName = UUID.randomUUID().toString();
        val questionTag = converter.convert(tagName);
        assertNull(questionTag.getQuestion());
        assertEquals(questionTag.getName(), tagName);
        assertNull(questionTag.getId());
    }

}