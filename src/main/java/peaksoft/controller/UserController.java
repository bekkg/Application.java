package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.User;
import peaksoft.service.impl.ApplicationService;
import peaksoft.service.impl.UserService;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ApplicationService applicationService;


    @Autowired
    public UserController(UserService userService, ApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @GetMapping()
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("appp",applicationService.findAll());
        return "user/save";
    }

    @PostMapping("/save")
    private String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.save(user);
        User user1 = userService.findById(user.getId());
        model.addAttribute("user22", user1);
        model.addAttribute("apps",applicationService.findAll());
        return "user/for-admin";
    }

    @GetMapping("/find-all")
    public String getAll(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "user/get-all";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("{id}")
    public String saveUpdate(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        if (user.getAuthorities().equals("ADMIN")) {
            return "user/for-admin";
        }
        return "redirect:find-all";
    }

    @GetMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:find-all";
    }

    @GetMapping("/profile/{id}")
    public String getUsers(@PathVariable("id") Long id,Model model){
        User user1 = userService.findById(id);
        model.addAttribute("user11", user1);
    return "user/profile";
    }

    @PostMapping("/download/{userid}/{appid}")
    public String addApplicationByUser(@PathVariable("userid")Long userid, @PathVariable("appid")Long appid, Model model) {
    userService.addApplicationByUser(userid, appid);
    User user1 = userService.findById(userid);
    model.addAttribute("user22", user1);
    model.addAttribute("apppp",applicationService.findById(appid));
    return "user/add-application";
    }

}
