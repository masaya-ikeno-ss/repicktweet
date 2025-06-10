package in.tech_camp.repicktweet.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.repicktweet.custom_user.CustomUserDetail;
import in.tech_camp.repicktweet.entity.TweetEntity;
import in.tech_camp.repicktweet.form.TweetForm;
import in.tech_camp.repicktweet.repository.TweetRepository;
import lombok.AllArgsConstructor;






@Controller
@AllArgsConstructor
public class TweetController {
  private final TweetRepository tweetRepository;
  
  @GetMapping("/")
  public String showTweets(Model model) {
    List<TweetEntity> tweets = tweetRepository.findAll();
    model.addAttribute("tweets", tweets);
    return "tweets/index";
  }
  
  @GetMapping("/tweets/new")
  public String showNewTweet(Model model) {
  model.addAttribute("tweetForm", new TweetForm());
  return "tweets/new";
  }

  @GetMapping("/tweets/{tweetId}/edit")
  public String editTweet(
    @PathVariable("tweetId") Integer tweetId,
    Model model
    ) {
      TweetEntity tweet = tweetRepository.findById(tweetId);

      TweetForm tweetForm = new TweetForm();
      tweetForm.setTitle(tweet.getTitle());
      tweetForm.setContent(tweet.getContent());
      tweetForm.setImageUrl(tweet.getImageUrl());

      model.addAttribute("tweetForm", tweetForm);
      model.addAttribute("tweetId", tweetId);
      return "tweets/edit";
  }
  

  @PostMapping("/tweets")
  public String createTweet(
    @ModelAttribute("tweetForm") TweetForm tweetForm,
    @AuthenticationPrincipal CustomUserDetail customUserDetail) {

    TweetEntity tweet = new TweetEntity();
    tweet.setTitle(tweetForm.getTitle());
    tweet.setContent(tweetForm.getContent());

    Integer userId = customUserDetail.getUser().getId();
    tweet.setUserId(userId);

    MultipartFile imageFile= tweetForm.getImageFile();
    if (imageFile != null && !imageFile.isEmpty()) {
      try {
        String uploadDir = "/home/masayaikeno/java_projects/repicktweet/uploads";

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(uploadDir, fileName);
        Files.copy(imageFile.getInputStream(), imagePath);
        tweet.setImageUrl("/uploads/" + fileName);
      } catch (IOException e) {
        System.out.println("エラー：" + e);
        return "tweets/new";
      }
    }

    try {
      tweetRepository.insert(tweet);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      return "redirect:/";
    }
    return "redirect:/";
  }

  @PostMapping("/tweets/{tweetId}/update")
  public String updateTweet(
    @ModelAttribute("tweetForm") @Validated TweetForm tweetForm,
    BindingResult result,
    @PathVariable("tweetId") Integer tweetId,
    Model model) {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.toList());
        model.addAttribute("errorMessages", errorMessages);

        model.addAttribute("tweetForm", tweetForm);
        model.addAttribute("tweetId", tweetId);
        return "tweets/edit";
      }

      TweetEntity tweet = tweetRepository.findById(tweetId);
      tweet.setTitle(tweetForm.getTitle());
      tweet.setContent(tweetForm.getContent());

      MultipartFile imageFile= tweetForm.getImageFile();
      if (imageFile != null && !imageFile.isEmpty()) {
        try {
          String uploadDir = "/home/masayaikeno/java_projects/repicktweet/uploads";

          String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + imageFile.getOriginalFilename();
          Path imagePath = Paths.get(uploadDir, fileName);
          Files.copy(imageFile.getInputStream(), imagePath);
          tweet.setImageUrl("/uploads/" + fileName);
        } catch (IOException e) {
          System.out.println("エラー：" + e);
          return "tweets/edit";
        }
      } else {
        tweet.setImageUrl(tweetForm.getImageUrl());
      }

      try {
        tweetRepository.update(tweet);
      } catch (Exception e) {
        System.out.println("エラー：" + e);
        return "redirect:/";
      }

      return "redirect:/";
  }
  
  
  @PostMapping("/tweets/{tweetId}/delete")
  public String delete(@PathVariable("tweetId") Integer tweetId) {
    try {
      tweetRepository.deleteTweet(tweetId);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      return "redirect:/";
    }
    return "redirect:/";
  }
}
