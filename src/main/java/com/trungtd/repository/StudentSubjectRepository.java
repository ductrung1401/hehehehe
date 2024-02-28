package com.trungtd.repository;

import com.trungtd.domain.Student;
import com.trungtd.domain.StudentSubject;
import com.trungtd.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {
     StudentSubject findByStudentAndSubject(Student student, Subject subject);

     interface MostRegisterSubject {
          Long getSubjectId();
          Long getNumOfStu();
     }

     @Query(value = "select ss2.fk_subject_id as 'subjectId', count(ss2.fk_student_id) as 'numOfStu' from student_subject_2 ss2\n" +
             " group by ss2.fk_subject_id\n" +
             " order by numOfStu desc", nativeQuery = true)
     Page<MostRegisterSubject> findMostRegisteredSubjects(Pageable pageable);

}