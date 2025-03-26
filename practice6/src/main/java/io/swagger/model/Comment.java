package io.swagger.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import org.springframework.validation.annotation.Validated;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.configuration.NotUndefined;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Comment
 */
@Validated
@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-19T08:52:43.423157093Z[GMT]")

@Entity
@Table(name = "comments")
public class Comment   {
  @JsonProperty("id")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id = null;

  @JsonProperty("author")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  @Column(nullable = false)
  private String author = null;

  @JsonProperty("text")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  @Column(nullable = false, columnDefinition = "TEXT")
  private String text = null;

  @JsonProperty("createdAt")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt = null;

  @JsonProperty("updatedAt")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private LocalDateTime updatedAt = null;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }


  public Comment id(UUID id) {

    this.id = id;
    return this;
  }

  /**
   * Уникальный идентификатор комментария
   * @return id
   **/
  
  @Schema(description = "Уникальный идентификатор комментария")
  
  public UUID getId() {
    return id;
  }



  public void setId(UUID id) {
    this.id = id;
  }

  public Comment author(String author) { 

    this.author = author;
    return this;
  }

  /**
   * Автор комментария
   * @return author
   **/
  
  @Schema(description = "Автор комментария")
  
  public String getAuthor() {  
    return author;
  }



  public void setAuthor(String author) { 
    this.author = author;
  }

  public Comment text(String text) { 

    this.text = text;
    return this;
  }

  /**
   * Текст комментария
   * @return text
   **/
  
  @Schema(description = "Текст комментария")
  
  public String getText() {  
    return text;
  }



  public void setText(String text) { 
    this.text = text;
  }

  public Comment createdAt(LocalDateTime createdAt) { 

    this.createdAt = createdAt;
    return this;
  }

  /**
   * Дата и время создания комментария
   * @return createdAt
   **/
  
  @Schema(description = "Дата и время создания комментария")
  
@Valid
  public LocalDateTime getCreatedAt() {  
    return createdAt;
  }



  public void setCreatedAt(LocalDateTime createdAt) { 
    this.createdAt = createdAt;
  }

  public Comment updatedAt(LocalDateTime updatedAt) { 

    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Дата и время последнего обновления комментария
   * @return updatedAt
   **/
  
  @Schema(description = "Дата и время последнего обновления комментария")
  
@Valid
  public LocalDateTime getUpdatedAt() {  
    return updatedAt;
  }



  public void setUpdatedAt(LocalDateTime updatedAt) { 
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment) o;
    return Objects.equals(this.id, comment.id) &&
        Objects.equals(this.author, comment.author) &&
        Objects.equals(this.text, comment.text) &&
        Objects.equals(this.createdAt, comment.createdAt) &&
        Objects.equals(this.updatedAt, comment.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, author, text, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
