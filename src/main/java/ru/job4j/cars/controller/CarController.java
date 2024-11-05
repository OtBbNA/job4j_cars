package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.service.car.CarService;
import ru.job4j.cars.service.post.PostService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"car"})
@AllArgsConstructor
public class CarController {

    private CarService carService;
    private PostService postService;

    @GetMapping({"create"})
    public String getCreatePage() {
        return "page/create";
    }

    @PostMapping({"create"})
    public String getCreatePage(@ModelAttribute Car car, Model model, HttpSession session) {
        carService.create(car);
        return "page/create";
    }

}
