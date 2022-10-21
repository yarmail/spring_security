package project.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.models.Person;

import java.util.Collection;

// Обертка над Person
public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // как бы не назывался метод у password здесь будет getPassword()
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    // не просрочен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // учетные данные не просрочены
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // включен, доступен
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Нужно, чтобы получать даннные аутентифицированного пользователя
    public Person getPerson() {
        return this.person;
    }
}