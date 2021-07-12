package ru.kurbanmagomedov.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 2, max = 45, message = "the length of username field should be from 2 to 45")
    private String username;

    @Column
    @Size(min = 8, max = 45, message = "the length of password field should be from 2 to 45")
    private String password;

    @Column
    @NotEmpty(message = "name field should not be empty")
    @Size(min = 2, max = 15, message = "the length of name field field should be from 2 to 15")
    private String name;

    @Column
    @NotEmpty(message = "lastname field should not be empty")
    @Size(min = 2, max = 15, message = "the lastname field should be from 2 to 30")
    private String lastname;

    @Column
    @Min(value = 1, message = "the age is incorrect")
    @Max(value = 130, message = "the age is incorrect")
    private int age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public String getStringRoles() {
        StringBuilder res = new StringBuilder();
        for (Role role : roles) {
            res.append(role.getRole().substring(5)).append(" ");
        }
        return res.toString();
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActiveRole(Role role) {
        return this.roles.contains(role);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


//    @Override
//    public String toString() {
//        return "User " + id + ": name = " + name + ", lastname = " + lastname + ", age = " + age +
//                ", username = " + username + ", roles = " + roles.toString();
//    }
}