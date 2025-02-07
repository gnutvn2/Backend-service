package vn.tungnv.backend_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity<T>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private T id;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updateAt;
}
