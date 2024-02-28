package com.trungtd.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_subject")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "fk_subject_id", referencedColumnName = "id")
    private Subject subject;
}
