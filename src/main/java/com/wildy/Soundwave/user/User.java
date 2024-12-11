//package com.wildy.Soundwave.user;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@Data
//@Builder
//@Inheritance(strategy = InheritanceType.JOINED)
//@NoArgsConstructor
//@AllArgsConstructor
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Email
//    @Column(unique = true)
//    private String email;
//
//    private String password;
//
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // Customize if needed
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // Customize if needed
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // Customize if needed
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true; // Customize if needed
//    }
//}

package com.wildy.Soundwave.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Builder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Tells JPA to use JOINED inheritance strategy
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Primary key for User (and inherited by Artist)
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Customize if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Customize if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Customize if needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // Customize if needed
    }
}

