package com.stackexchange.featuredquestions.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Converter<FROM, TO> {
    TO convert(FROM from);

    default List<TO> convertAll(Collection<FROM> fromCollection) {
        return fromCollection.stream().map(this::convert).collect(Collectors.toList());
    }
}
