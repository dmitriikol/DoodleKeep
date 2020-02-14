package com.dmitriikol.controller;

import com.dmitriikol.model.User;
import com.dmitriikol.repository.NoteRepository;
import com.dmitriikol.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepo;

    private static Logger log = Logger.getLogger(UserController.class);

    @GetMapping("/index")
    public String index(Model model) {
        log.info("/index");
        model.addAttribute("users", userRepo.findAll());
        return "index";
    }

    @GetMapping("/addUser")
    public String showFormAddUser(Model model) {
        model.addAttribute("user" , new User());
        return "add-user";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        log.info("add new user: " + user.getId() + ", " + user.getName());
        userRepo.save(user);
        model.addAttribute("users", userRepo.findAll());
        return "redirect:/index";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        User user = userRepo.findById(id).get();
        log.info("delete user: " + user.getId() + ", " + user.getName());
        userRepo.delete(user);
        model.addAttribute("users", userRepo.findAll());
        return "redirect:/index";
    }

}
