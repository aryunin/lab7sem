package com.example.demo.controller;


import com.example.demo.entity.Movie;
import com.example.demo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping
    public String getAll(Model model) {
        var movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "index";
    }

    @GetMapping("/new")
    public String getCreationForm(@ModelAttribute Movie movie) {
        return "new";
    }

    @PostMapping
    public String save(@ModelAttribute Movie movie) {
        var x = movieRepository.insert(movie);
        return "redirect:/";
    }
}
