package com.example.Vapor.config;

//import com.example.demo.models.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;

public class MyUserDetails {

}

//public class MyUserDetails implements UserDetails {
//
//    private final String username;
//    private final String password;
//    private final List<GrantedAuthority> authorities;
//    private final boolean active;
//
//    public MyUserDetails(User user){
//        username = user.getUsername();
//        password = user.getUser_password();
//        authorities = Arrays.stream(user.getUser_roles().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(toList());
//        active = user.isActive();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return active;
//    }
//}
