package com.daviipkp.lifedashboard.latest.instance;

import com.daviipkp.lifedashboard.latest.instance.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "userauthdata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAuthData implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    private String email;
    private String username;
    private String fullname;

    @JsonIgnore
    private long contentID;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private boolean enabled;

    public UserAuthData(String email, String username, String password, UserRole userRole, boolean enabled) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.enabled = enabled;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userRole == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {return true;}

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {
        return true;
    }
}
