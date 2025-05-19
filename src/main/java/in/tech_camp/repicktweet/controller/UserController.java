package in.tech_camp.repicktweet.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.repicktweet.entity.UserEntity;
import in.tech_camp.repicktweet.form.UserForm;
import in.tech_camp.repicktweet.repository.UserRepository;
import in.tech_camp.repicktweet.service.UserService;
import in.tech_camp.repicktweet.validation.ValidationOrder;
import lombok.AllArgsConstructor;





@Controller
@AllArgsConstructor
public class UserController {
  private final UserRepository userRepository;
  private final UserService userService;

  @GetMapping("/users/sign_up")
  public String showSignUp(Model model) {
    model.addAttribute("userForm", new UserForm());
    return "users/signUp";
  }

  @PostMapping("/user")
  public String createUser(
    @ModelAttribute("userForm") @Validated(ValidationOrder.class) UserForm userForm,
    BindingResult result,
    Model model
  ) {
    userForm.validatePasswordConfirmation(result);
    if (userRepository.existsByEmail(userForm.getEmail())) {
      result.rejectValue("email", "null", "このメールアドレスは既に登録されています");
    }
    if (result.hasErrors()) {
      List<String> errorMessages = result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
      
      model.addAttribute("errorMessages", errorMessages);
      model.addAttribute("userForm", userForm);
      return "users/signUp";
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setNickname(userForm.getNickname());
    userEntity.setEmail(userForm.getEmail());
    userEntity.setPassword(userForm.getPassword());

    try {
      userService.createUserWithEncryptedPassword(userEntity);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      return "redirect:/";
    }

    return "redirect:/";
  }
  
  @GetMapping("/users/login")
  public String showLogin() {
      return "users/login";
  }
  
  @GetMapping("/login")
  public String showLoginWithError(
    @RequestParam(value = "error") String error, 
    Model model) {
      if (error != null) {
        model.addAttribute("loginError", "メールアドレスもしくはパスワードが間違っています");
      }
      return "users/login";
  }
  
}
