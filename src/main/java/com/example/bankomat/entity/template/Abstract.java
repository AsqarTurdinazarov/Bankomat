package com.example.bankomat.entity.template;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Abstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedBy
    private Integer CreateBy;
    @LastModifiedBy
    private Integer UpdateBy;

    @CreationTimestamp
    private Timestamp CreateTime;
    @UpdateTimestamp
    private Timestamp UpdateTime;
}
