package com.rodrigopeleias.bookstoremanager.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class Auditing {

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;
}
