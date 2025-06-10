package in.tech_camp.repicktweet.form;

import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.repicktweet.validation.ValidationPriority1;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TweetForm {

  @NotBlank(message = "タイトルを入力してください", groups = ValidationPriority1.class)
  private String title;

  @NotBlank(message = "投稿内容を入力してください", groups = ValidationPriority1.class)
  private String content;

  // 新規・差し替え画像用
  private MultipartFile imageFile;

  // 既存画像表示用
  private String imageUrl;
}
