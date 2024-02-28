package com.trungtd.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "student")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "DoB")
    private Date DoB;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "gender")
    private String gender;
    @ManyToMany
    @JoinTable(name = "student_subject_2",
            joinColumns = @JoinColumn(name = "fk_student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_subject_id", referencedColumnName = "id"))
    private Set<Subject> subjectSet;
}