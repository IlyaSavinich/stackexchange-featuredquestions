package com.stackexchange.featuredquestions.infra.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StackExchangePage<ITEM> {
    private List<ITEM> items;
}
