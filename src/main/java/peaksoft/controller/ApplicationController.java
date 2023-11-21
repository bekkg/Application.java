package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Application;
import peaksoft.model.Genre;
import peaksoft.model.User;
import peaksoft.service.impl.ApplicationService;
import peaksoft.service.impl.GenreService;
import peaksoft.service.impl.UserService;

import java.security.Principal;
import java.util.List;
@Controller
@RequestMapping("/application")

public class ApplicationController {

    private final ApplicationService applicationService;
    private final GenreService genreService;
    private final UserService userService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, GenreService genreService, UserService userService) {
        this.applicationService = applicationService;
        this.genreService = genreService;
        this.userService = userService;
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
    @ModelAttribute("/applist")
    public List<Application> getApp(){
        return applicationService.findAll();
    }
    @GetMapping("/main")
    public String main(){
        return "application/main-app";
    }

    @GetMapping("/my-Application")
    public String getApplicationByUser(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        List<Application> myApplications = applicationService.getApplicationByUser(user.getId());
        model.addAttribute("myApplications",myApplications);
        return "application/user-application";
    }
    @GetMapping("/search")
    public String findApplicationByName(String name,Model model){
        if(name == null){
            model.addAttribute("appList", applicationService.findAll() );
        }else {
            List<Application> applicationList = applicationService.findApplicationByUser(name);
            model.addAttribute("appList",applicationList);
        }
        return "application/search";
    }

}
