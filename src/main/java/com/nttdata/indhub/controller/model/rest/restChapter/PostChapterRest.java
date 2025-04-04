package com.nttdata.indhub.controller.model.rest.restChapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Esta clase representa un capítulo que se puede publicar a través de una API REST.
 * Contiene información sobre el capítulo, incluyendo su ID, número, nombre y duración.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostChapterRest {

  @Setter(value = AccessLevel.NONE)
  @JsonProperty
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long id;

  private Integer number;

  @NotNull
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long duration;
}
