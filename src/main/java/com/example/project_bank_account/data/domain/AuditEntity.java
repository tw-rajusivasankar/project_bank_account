package com.example.project_bank_account.data.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    protected UUID uuid;

    @Column(name = "created_datetime", nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createdDateTime;

    @Column(name = "modified_datetime")
    @LastModifiedDate
    protected LocalDateTime modifiedDateTime;
}