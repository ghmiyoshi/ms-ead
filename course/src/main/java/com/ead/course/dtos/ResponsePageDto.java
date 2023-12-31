package com.ead.course.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class ResponsePageDto<T> extends PageImpl<T> {

  /* Indico para o jackson usar isso na deserialização */
  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public ResponsePageDto(@JsonProperty("content") final List<T> content,
      @JsonProperty("number") final int number,
      @JsonProperty("size") final int size,
      @JsonProperty("total_elements") final Long totalElements,
      @JsonProperty("pageable") final JsonNode pageable,
      @JsonProperty("last") final boolean last,
      @JsonProperty("first") final boolean first,
      @JsonProperty("empty") final boolean empty,
      @JsonProperty("total_pages") final int totalPages,
      @JsonProperty("sort") final JsonNode sort,
      @JsonProperty("number_of_elements") final int numberOfElements) {
    super(content, PageRequest.of(number, 1), 10);
  }
}
