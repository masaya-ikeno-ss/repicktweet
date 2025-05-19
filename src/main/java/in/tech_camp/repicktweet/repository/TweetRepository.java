package in.tech_camp.repicktweet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.repicktweet.entity.TweetEntity;

@Mapper
public interface TweetRepository {
  @Select("SELECT * FROM tweets")
  @Results({
    @Result(property = "userId", column = "user_id"),
    @Result(property = "imageUrl", column = "image_url"),
    @Result(property = "createdAt", column = "created_at"),
    @Result(property = "updatedAt", column = "updated_at"),
    @Result(property = "deletedAt", column = "deleted_at")
  })
  List<TweetEntity> findAll();
}
