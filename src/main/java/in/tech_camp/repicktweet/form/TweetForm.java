package in.tech_camp.repicktweet.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TweetForm {
  private String title;
  private String content;
  private MultipartFile imageFile;
}
