package com.rodrigopeleias.bookstoremanager.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedBy
    protected String createdBy;

    @LastModifiedBy
    protected String lastModifiedBy;

    @CreatedDate
    @Column(nullable = false)
    protected LocalDateTime createdDate;

    @LastModifiedDate
    @Column
    protected LocalDateTime lastModifiedDate;
}
