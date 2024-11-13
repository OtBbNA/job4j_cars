package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.car.CarService;
import ru.job4j.cars.service.post.PostService;
import ru.job4j.cars.service.price.PriceHistoryService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"post"})
@AllArgsConstructor
public class PostController {

    private CarService carService;
    private PriceHistoryService priceHistoryService;
    private PostService postService;

    @GetMapping({"create"})
    public String getCreatePage() {
        return "page/create";
    }

    @PostMapping({"create"})
    public String getCreatePage(@ModelAttribute Post post, @RequestParam List<MultipartFile> files, Model model, HttpSession session) {
        carService.create(post.getCar());
        for (PriceHistory ph: post.getPriceHistories()) {
            priceHistoryService.create(ph);
        }
        post.setUser((User) session.getAttribute("user"));
        try {
            List<FileDto> postFiles = new ArrayList<>();
            for (var mpf : files) {
                postFiles.add(new FileDto(mpf.getOriginalFilename(), mpf.getBytes()));
            }
            postService.create(post, postFiles);
            return "/";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
}
