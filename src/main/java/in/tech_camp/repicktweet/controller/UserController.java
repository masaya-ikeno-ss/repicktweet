package in.tech_camp.repicktweet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.repicktweet.form.UserForm;


@Controller
public class UserController {
  @GetMapping("/users/sign_up")
  public String showSignUp(Model model) {
    model.addAttribute("userForm", new UserForm());
    return "users/signUp";
  }
  
}
