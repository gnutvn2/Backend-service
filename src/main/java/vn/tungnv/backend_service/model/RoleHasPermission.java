package vn.tungnv.backend_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_role_has_permission")
public class RoleHasPermission extends AbstractEntity<Long> implements Serializable {
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
