package in.tech_camp.repicktweet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.repicktweet.entity.TweetEntity;

@Mapper
public interface TweetRepository {
  @Select("SELECT * FROM tweets WHERE deleted_at IS null")
  @Results({
    @Result(property = "userId", column = "user_id"),
    @Result(property = "imageUrl", column = "image_url"),
    @Result(property = "createdAt", column = "created_at"),
    @Result(property = "updatedAt", column = "updated_at"),
    @Result(property = "deletedAt", column = "deleted_at")
  })
  List<TweetEntity> findAll();

  @Insert("INSERT INTO tweets (title, content, image_url, user_id) VALUES (#{title}, #{content}, #{imageUrl}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(TweetEntity tweet);

  @Update("UPDATE tweets SET deleted_at = CURRENT_TIMESTAMP WHERE id = #{id}")
  void deleteTweet(Integer id);

  @Select("SELECT * FROM tweets WHERE id = #{id} AND deleted_at IS null")
  @Results({
    @Result(property = "userId", column = "user_id"),
    @Result(property = "imageUrl", column = "image_url"),
    @Result(property = "createdAt", column = "created_at"),
    @Result(property = "updatedAt", column = "updated_at"),
    @Result(property = "deletedAt", column = "deleted_at")
  })
  TweetEntity findById(Integer id);

  @Update("UPDATE tweets SET title = #{title}, content = #{content}, image_url = #{imageUrl}, updated_at = CURRENT_TIMESTAMP WHERE id = #{id} AND deleted_at IS null")
  void update(TweetEntity tweet);

}
