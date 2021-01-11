package ru.tehauth.demo.controllers;

import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tehauth.demo.domain.Post;
import ru.tehauth.demo.repo.PostRepo;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostRepo postRepo;

    @Autowired
    public BlogController(PostRepo postRepo) {this.postRepo = postRepo;}

    @GetMapping
    public String greeting(Model model) {
        model.addAttribute("title", "Блог");
        Iterable<Post> all = postRepo.findAll();
        model.addAttribute("posts", all);
        return "blog-main";
    }

    @GetMapping("/add")
    public String modelAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,@RequestParam String anons, @RequestParam String full_text,Model model){
        Post post = new Post();
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setTitle(title);
        this.postRepo.save(post);
        return "redirect:/blog";
    }

}
