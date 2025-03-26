package io.swagger.controller;

import io.swagger.model.Comment;
import io.swagger.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Comment> getComments() {
        logger.info("Получение всех комментариев");
        return service.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable UUID id) {
        logger.info("Получение комментария с ID: {}", id);
        return service.getComment(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Map<String, String> request) {
        String author = request.get("author");
        String text = request.get("text");
        logger.info("Создание комментария от автора: {}", author);
        return service.createComment(author, text);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable UUID id) {
        logger.info("Удаление комментария с ID: {}", id);
        service.deleteComment(id);
    }
}
