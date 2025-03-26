package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.configuration.NotUndefined;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommentCreate
 */
@Validated
@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-19T08:52:43.423157093Z[GMT]")


public class CommentCreate   {
  @JsonProperty("author")

  private String author = null;

  @JsonProperty("text")

  private String text = null;


  public CommentCreate author(String author) { 

    this.author = author;
    return this;
  }

  /**
   * Автор комментария
   * @return author
   **/
  
  @Schema(required = true, description = "Автор комментария")
  
  @NotNull
  public String getAuthor() {  
    return author;
  }



  public void setAuthor(String author) { 

    this.author = author;
  }

  public CommentCreate text(String text) { 

    this.text = text;
    return this;
  }

  /**
   * Текст комментария
   * @return text
   **/
  
  @Schema(required = true, description = "Текст комментария")
  
  @NotNull
  public String getText() {  
    return text;
  }



  public void setText(String text) { 

    this.text = text;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentCreate commentCreate = (CommentCreate) o;
    return Objects.equals(this.author, commentCreate.author) &&
        Objects.equals(this.text, commentCreate.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, text);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentCreate {\n");
    
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
