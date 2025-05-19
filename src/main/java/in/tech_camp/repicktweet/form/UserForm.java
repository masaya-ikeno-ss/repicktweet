package in.tech_camp.repicktweet.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {

  @NotBlank(message = "ニックネームが入力されていません")
  @Length(max = 20, message = "ニックネームは20文字以内で入力してください")
  private String nickname;

  @NotBlank(message = "メールアドレスが入力されていません")
  @Email(message = "メールアドレスがフォーマットに則っていません")
  private String email;

  @NotBlank(message = "パスワードが入力されていません")
  @Length(min = 6, max = 128, message = "パスワードは6文字以上128文字以内で入力してください")
  private String password;

  @NotBlank(message = "確認用パスワードが入力されていません")
  @Length(min = 6, max = 128, message = "確認用パスワードは6文字以上128文字以内で入力してください")
  private String passwordConfirmation;

  public void validatePasswordConfirmation(BindingResult result) {
    if (!password.equals(passwordConfirmation)) {
      result.rejectValue("passwordConfirmation", null, "パスワードと確認用パスワードが一致しません");
    }
  }
}
