package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.services.PersonDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //настраиваем конфигурацию, авторизацию
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // временно отключаем csrf токен (защиту от межсайтовой подделки запросов)
                .authorizeRequests() // начало авторизации -  предоставить разрешения для следующих url
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll() // на эти страниы проходят все
                .anyRequest().authenticated() // на все остальные страницы мы не пускаем неаутентиф. пользователей
                .and() //разделитель между блоками
                .formLogin().loginPage("/auth/login") //хотим свою страницу аутентификации
                .loginProcessingUrl("/process_login") //куда отправлять данные из формы аутентификации
                .defaultSuccessUrl("/hello", true) // после успешной аутентиф. перенаправить на страницу
                .failureUrl("/auth/login?error") //после неуспешной аутентиф. перенаправить обратно с ключом
                .and() // добавляем процесс разлогининивания
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login"); // ссылка на разлогинивание, автопереход после него


    }

    //Настраиваем аутентификацию используя bcrypt
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    /**
     * Как шифруется пароль
     * NoOpPasswordEncoder.getInstance(); - никак не шифруется
     * BCryptPasswordEncoder() - используем алгоритм bcrypt
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}