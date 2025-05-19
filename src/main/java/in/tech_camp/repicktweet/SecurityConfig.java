package in.tech_camp.repicktweet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        //ここに記述されたGETリクエストは許可されます（ログイン不要です)
                        .requestMatchers("/css/**", "/images/**", "/", "/users/sign_up", "/users/login", "/tweets/{id:[0-9]+}").permitAll()
                        //ここに記述されたPOSTリクエストは許可されます(ログイン不要です)
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()
                        //上記以外のリクエストは認証されたユーザーのみ許可されます(要ログイン)
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        //ログインフォームでログインボタンを押した際のパスを設定しています
                        .loginProcessingUrl("/login")
                        //ログインフォームのパスを設定しています
                        .loginPage("/users/login")
                        //ログイン成功後のリダイレクト先です
                        .defaultSuccessUrl("/", true)
                        //ログイン失敗後のリダイレクト先です
                        .failureUrl("/login?error")
                        //ログイン時にusernameとして扱うパラメーターを指定します
                        .usernameParameter("email") 
                        .permitAll())

                .logout(logout -> logout
                        //ログアウトボタンを押した際のパスを設定しています
                        .logoutUrl("/logout")
                        //ログアウト成功時のリダイレクト先です
                        .logoutSuccessUrl("/"));

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
