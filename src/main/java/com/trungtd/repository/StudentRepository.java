package com.trungtd.repository;

import com.trungtd.domain.Student;
import com.trungtd.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE SIZE(s.subjectSet) = :size")
    List<Student> findStudentsBySubjectSetSize(@Param("size") Long size);
    Set<Student> findBySubjectSetContaining(Subject subject);
}