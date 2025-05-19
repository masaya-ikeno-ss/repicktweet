package in.tech_camp.repicktweet.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.tech_camp.repicktweet.custom_user.CustomUserDetail;
import in.tech_camp.repicktweet.entity.UserEntity;
import in.tech_camp.repicktweet.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAuthenticationService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException("このメールアドレスではユーザーが登録されていません：" + email);
    }
    return new CustomUserDetail(userEntity);
  }


}
