package in.tech_camp.repicktweet.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TweetEntity {
  private Integer id;
  private String title;
  private Integer UserId;
  private String content;
  private String imageUrl;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp deletedAt;
}
