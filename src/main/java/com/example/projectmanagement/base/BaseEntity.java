package com.example.projectmanagement.base;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // GenerationType.AUTO dung mot chuoi cho toan bang,
    // GenerationType.IDENTITY dung 1 chuoi trong moi bang duy nhat
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(updatable = false)
    private long createBy;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    private long updateBy;

    @Column
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
}
