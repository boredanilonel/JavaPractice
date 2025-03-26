package io.swagger.service;

import io.swagger.model.Comment;
import io.swagger.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> getAllComments() {
        logger.info("Запрос на получение всех комментариев");
        return new ArrayList<>(repository.findAll());
    }

    public Comment getComment(UUID uuid) {
        logger.info("Запрос на получение комментария с ID: {}", uuid);
        return repository.findById(uuid)
                .orElseThrow(() -> {
                    logger.warn("Комментарий с ID {} не найден", uuid);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден");
                });
    }

    public Comment createComment(String author, String text) {
        logger.info("Создание нового комментария от автора: {}", author);
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setText(text);
        Comment saved = repository.save(comment);
        logger.info("Комментарий создан с ID: {}", saved.getId());
        return saved;
    }

    public void deleteComment(UUID id) {
        logger.info("Попытка удаления комментария с ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Комментарий с ID {} не найден, удаление невозможно", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден");
        }
        repository.deleteById(id);
        logger.info("Комментарий с ID {} успешно удалён", id);
    }
}
