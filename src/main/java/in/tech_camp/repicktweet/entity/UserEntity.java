package in.tech_camp.repicktweet.entity;

import lombok.Data;

@Data
public class UserEntity {
  private Integer id;
  private String nickName;
  private String email;
  private String password;
}
