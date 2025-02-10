package vn.tungnv.backend_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "tbl_role")
public class Role extends AbstractEntity<Long> implements Serializable {
    @Column(name = "name", unique = true)
    private String name;
    @OneToMany(mappedBy = "role")
    private Set<RoleHasPermission> roles = new HashSet<>();
}
