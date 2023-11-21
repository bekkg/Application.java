package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Genre;


import peaksoft.service.impl.GenreService;

@Controller
@RequestMapping("/genre")

public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/add")
    public String addGenre(Model model) {
        model.addAttribute("genres", new Genre());
        return "genre/save";
    }

    @PostMapping("/save")
    private String saveGenre(@ModelAttribute("genres") Genre genre) {
        genreService.save(genre);
        return "redirect:find-all";
    }

    @GetMapping("/find-all")
    public String getAllGenre(Model model) {
        model.addAttribute("genreList", genreService.findAll());
        return "genre/get-all";
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Genre genre = genreService.findById(id);
        model.addAttribute("genre",genre);

        return "genre/update";
    }

    @PostMapping("{id}")
    public String saveUpdate(@PathVariable("id") Long id, @ModelAttribute("genre")Genre genre){
        genreService.update(id,genre);
        return "redirect:find-all";
    }

    @GetMapping("{id}")
    public String delete(@PathVariable("id") Long id){
        genreService.deleteById(id);
        System.out.println("success delete Genre Controller");
        return "redirect:find-all";
    }

    @GetMapping("/main")
    public String all (){
        return "genre/main-genre";
    }
}
