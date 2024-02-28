package com.trungtd.service;

import com.trungtd.domain.Student;
import com.trungtd.domain.Subject;
import com.trungtd.domain.SubjectStatistics;

import java.util.List;
import java.util.Set;

public interface FunctionsBetweenStuAndSub {
    Student registerSubForStu(Long studentId, Long subjectId);
    Student deleteRegisteredSubOfStu(Long studentId, Long subjectId);
    Set<Subject> getSubjectsByStudentId(Long id);
    List<Student> getStudentsBySizeSubjectSet(Long size);
    Set<Student> getStudentsBySubjectId(Long subId);
    List<SubjectStatistics> getMostPopularSubject(int quantity);
}
