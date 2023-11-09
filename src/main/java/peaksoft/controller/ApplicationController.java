package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Application;
import peaksoft.model.Genre;
import peaksoft.service.impl.ApplicationService;
import peaksoft.service.impl.GenreService;

import java.util.List;

@Controller
@RequestMapping("/application")

public class ApplicationController {

    private final ApplicationService applicationService;
    private final GenreService genreService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, GenreService genreService) {
        this.applicationService = applicationService;
        this.genreService = genreService;
    }

    @GetMapping("/add")
    public String addApplication(Model model) {
        model.addAttribute("applications", new Application());
        return "application/save";
    }

    @PostMapping("/save")
    private String saveApplication(@ModelAttribute("application") Application application) {
        applicationService.save(application);
        return "redirect:find-all";
    }

    @GetMapping("/find-all")
    public String getAllApplication(Model model) {
        model.addAttribute("applicationList", applicationService.findAll());
        return "application/get-all";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Application application = applicationService.findById(id);
        model.addAttribute("app",application);
        return "application/update";
    }

    @PostMapping("{id}")
    public String saveUpdate(@PathVariable("id") Long id, @ModelAttribute("application")Application application){
        applicationService.update(id,application);
        return "redirect:find-all";
    }

    @GetMapping("{id}")
    public String delete(@PathVariable("id") Long id){
        applicationService.deleteById(id);
        return "redirect:find-all";
    }


    @ModelAttribute("genreList")
    public List<Genre>getGenres(){
        return genreService.findAll();
    }

}
