package ru.tehauth.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.tehauth.demo.domain.Post;
import ru.tehauth.demo.repo.PostRepo;


@Controller
public class BlogController {

    private final PostRepo postRepo;

    @Autowired
    public BlogController(PostRepo postRepo) {this.postRepo = postRepo;}

    @GetMapping("/blog")
    public String greeting(Model model) {
        model.addAttribute("title", "Блог");
        Iterable<Post> all = postRepo.findAll();
        model.addAttribute("posts", all);
        return "blog-main";
    }
}
