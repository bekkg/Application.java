package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.MailSender;
import peaksoft.service.impl.MailSenderService;

@Controller
@RequestMapping("/mailsender")
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @Autowired
    public MailSenderController(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @GetMapping("/add")
    public String addMailSender(Model model) {
        model.addAttribute("mailSender1", new MailSender());
        System.out.println("success add Mailsender Controller");
        return "mailsender/save";
    }

    @PostMapping("/save")
    private String saveMailSender(@ModelAttribute("mailSender") MailSender mailSender) {
        mailSenderService.save(mailSender);
        System.out.println("success save Mailsender Controller");
        return "redirect:find-all";
    }

    @GetMapping("/find-all")
    public String getAllMailSender(Model model) {
        model.addAttribute("mailSenderList", mailSenderService.findAll());
        System.out.println("success getall Mailsender Controller");
        return "mailsender/get-all";
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        MailSender mailSender = mailSenderService.findById(id);
        model.addAttribute("mail",mailSender);
        return "mailsender/update";
    }

    @PostMapping("{id}")
    public String saveUpdate(@PathVariable("id") Long id, @ModelAttribute("mailSender")MailSender mailSender){
        mailSenderService.update(id,mailSender);
        return "redirect:find-all";
    }

    @GetMapping("{id}")
    public String delete(@PathVariable("id") Long id){
        mailSenderService.deleteById(id);
        return "redirect:find-all";
    }

}
