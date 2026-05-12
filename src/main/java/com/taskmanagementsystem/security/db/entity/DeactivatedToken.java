package com.taskmanagementsystem.security.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeactivatedToken {

    @Id
    private UUID id;

    @Column(name = "keep_until")
    private Date keepUntil;
}
