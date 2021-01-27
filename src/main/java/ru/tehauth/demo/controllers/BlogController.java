package ru.tehauth.demo.controllers;


import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tehauth.demo.domain.Post;
import ru.tehauth.demo.domain.Views;
import ru.tehauth.demo.repo.PostRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Value("${upload.path}")
    private String uploadPath;

    private final PostRepo postRepo;

    @Autowired
    public BlogController(PostRepo postRepo) {this.postRepo = postRepo;}

    @GetMapping
    public String greeting(Model model) {
        model.addAttribute("title", "Блог");
        Iterable<Post> all = postRepo.findAllByStatusTrue();
        model.addAttribute("posts", all);
        return "blog-main";
    }

    @GetMapping("/add")
    public String modelAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text,
                      @RequestParam MultipartFile multipartFile,
                      Model model) {
        Post post = new Post();
        if (multipartFile.getSize() != 0) {
            File fileUploadPath = new File(uploadPath);
            String upFile = "/img";
            File uploadPDir = new File(fileUploadPath.getAbsolutePath() + upFile);
            if (!uploadPDir.exists()) {
                uploadPDir.mkdirs();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(new File(uploadPDir + "/" + resultFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.setImg("static" + upFile + "/" + resultFilename);

        }else {
            post.setImg(null);
        }
        post.setLocalDate(LocalDateTime.now());
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setTitle(title);
        this.postRepo.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable String id, HttpServletRequest httpServletRequest, Model model) {

        if (!this.postRepo.existsById(id))
            return "redirect:/blog";
        Post post = postRepo.findAllById(id);
        if (post.getViews() == null)
            post.setViews(new HashSet());
        else {
            boolean contains = post.getViews().stream()
                                   .anyMatch(views -> views.getIp().equals(httpServletRequest.getRemoteAddr()));
            if (!contains) {
                post.getViews().add(new Views(httpServletRequest.getRemoteAddr()));
            }
        }
        try {
            postRepo.save(post);
        } catch (Exception e) {
        }


        model.addAttribute("views", post.getViews().size());
        model.addAttribute("post", post);
        return "blog-details";
    }

    @GetMapping("/{id}/edit")
    public String getEditPost(@PathVariable String id, Model model) {
        if (!this.postRepo.existsById(id))
            return "redirect:/blog";
        Post post = postRepo.findAllById(id);
        model.addAttribute("post", post);
        return "blog-edit";
    }

    @PostMapping("/{id}/remove")
    public String removePost(@PathVariable String id, Model model) {
        Post post = postRepo.findAllById(id);
        post.setStatus(false);
        postRepo.save(post);

        return "redirect:/";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable String id, @RequestParam String title, @RequestParam String anons,
                           @RequestParam String full_text, @RequestParam MultipartFile multipartFile, Model model) {

            if (!this.postRepo.existsById(id))
                return "redirect:/blog";
            Post post = postRepo.findAllById(id);
        if (multipartFile.getSize() != 0) {
            File fileUploadPath = new File(uploadPath);
            String upFile = "/img";
            File uploadPDir = new File(fileUploadPath.getAbsolutePath() + upFile);
            if (!uploadPDir.exists()) {
                uploadPDir.mkdirs();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(new File(uploadPDir + "/" + resultFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.setImg("static" + upFile + "/" + resultFilename);

        }

        post.setLocalDate(LocalDateTime.now());
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setTitle(title);
        this.postRepo.save(post);
        return "redirect:/";
    }
}
