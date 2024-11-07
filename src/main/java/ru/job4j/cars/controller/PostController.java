package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.car.CarService;
import ru.job4j.cars.service.post.PostService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"post"})
@AllArgsConstructor
public class PostController {

    private CarService carService;
    private PostService postService;

    @GetMapping({"create"})
    public String getCreatePage() {
        return "page/create";
    }

    @PostMapping({"create"})
    public String getCreatePage(@ModelAttribute Post post, @RequestParam MultipartFile file, Model model, HttpSession session) {
        postService.create(post);
        return "page/create";
    }

}
