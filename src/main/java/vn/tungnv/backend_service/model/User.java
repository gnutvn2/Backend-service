package vn.tungnv.backend_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.tungnv.backend_service.common.Gender;
import vn.tungnv.backend_service.common.UserStatus;
import vn.tungnv.backend_service.common.UserType;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
public class User extends AbstractEntity<Long> implements UserDetails, Serializable {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private UserStatus userStatus;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type")
    private UserType userType;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserHasRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<GroupHasUser> groups = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // 1.Get role
        List<Role> roleList = roles.stream().map(UserHasRole::getRole).toList();

        // 2.Get role name
        List<String> roleNames = roleList.stream().map(Role::getName).toList();

        // 3.Add role name to authority
        return roleNames.stream().map(SimpleGrantedAuthority::new).toList();

    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(userStatus);
    }
}
