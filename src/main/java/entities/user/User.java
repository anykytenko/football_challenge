package entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by ANykytenko on 8/2/2016.
 */
public class User implements UserDetails {

    private String lastName;

    private String firstName;

    private String userName;

    @JsonIgnore
    private String password;

    private String email;

    private String linkToAvatar;

    private Role role;

    private Integer id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkToAvatar() {
        return linkToAvatar;
    }

    public void setLinkToAvatar(String linkToAvatar) {
        this.linkToAvatar = linkToAvatar;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>() {{
            add(new SimpleGrantedAuthority(getRole().name()));
        }};
    }

    public String getUsername() {
        return getUserName();
    }

    public boolean isAccountNonExpired() {
        return false;
    }

    public boolean isAccountNonLocked() {
        return false;
    }

    public boolean isCredentialsNonExpired() {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "{\"id\": " + id + "; \"firstName\": \"" + firstName + "\"; \"lastName\": \"" + lastName +"\"}";
    }

    public enum Role {
        ADMIN(1),
        USER(2);

        private int id;

        Role(int id) {
            this.id = id;
        }

        public static Role getById(int id) {
            for (Role role : Role.values()) {
                if (role.id == id) {
                    return role;
                }
            }
            return null;
        }
    }
}
