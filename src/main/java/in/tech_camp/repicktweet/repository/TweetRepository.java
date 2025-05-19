package in.tech_camp.repicktweet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.repicktweet.entity.TweetEntity;

@Mapper
public interface TweetRepository {
  @Select("SELECT * FROM tweets")
  List<TweetEntity> findAll();
}
