package ru.tehauth.demo.repo;

import org.springframework.data.repository.CrudRepository;
import ru.tehauth.demo.domain.Post;

public interface PostRepo extends CrudRepository<Post, String> {
}
