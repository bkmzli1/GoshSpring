package ru.tehauth.demo.repo;

import org.springframework.data.repository.CrudRepository;
import ru.tehauth.demo.domain.Post;

import java.util.Set;

public interface PostRepo extends CrudRepository<Post, String> {
    Post findAllById(String id);
    Set<Post> findAllByStatusTrue();
}
