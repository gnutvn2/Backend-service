package vn.tungnv.backend_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_group")
public class Group extends AbstractEntity<Long> implements Serializable {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "role_id")
    @OneToOne
    private Role role;
}
