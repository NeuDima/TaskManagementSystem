package com.taskmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"tasksAuthor", "tasksExecutor"})
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id")
    private Role role;


    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Builder.Default
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> tasksAuthor = new ArrayList<>();

    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    @Builder.Default
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> tasksExecutor = new ArrayList<>();
}