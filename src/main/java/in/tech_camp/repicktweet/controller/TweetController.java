package in.tech_camp.repicktweet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.repicktweet.entity.TweetEntity;
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
  
}
