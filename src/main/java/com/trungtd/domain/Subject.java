package com.trungtd.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "subCode")
    private String subCode;
    @Column(name = "name")
    private String name;
    @Column(name = "NoC")
    private Integer NoC;
//    @ManyToMany(mappedBy = "subjectSet")
//    private Set<Student> studentSet = new HashSet<>();

}
