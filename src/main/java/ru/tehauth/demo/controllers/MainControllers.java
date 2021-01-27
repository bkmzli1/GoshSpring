package ru.tehauth.demo.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.tehauth.demo.domain.Post;
import ru.tehauth.demo.repo.PostRepo;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class MainControllers {
    @Value("${upload.path}")
    private String uploadPath;

    private final PostRepo postRepo;

    public MainControllers(PostRepo postRepo) {this.postRepo = postRepo;}


    @GetMapping("/")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public String greeting(Model model){
        model.addAttribute("title" , "Главная Страница");
        Set<Post> allPost = postRepo.findAllByStatusTrue();
        Set<Post> posts = new TreeSet<>(Comparator.comparing(Post::getLocalDate).reversed());
        posts.addAll(allPost);
        model.addAttribute("posts", posts);
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title" , "О нас");
        return "about";
    }




}
