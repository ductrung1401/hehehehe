package com.trungtd.service;

import com.trungtd.domain.StudentSubject;
import com.trungtd.domain.Subject;

import java.util.Set;

public interface StudentSubjectService {
    StudentSubject registerStudentForSubject(Long studentId, Long subjectId);
    Set<Subject> getSubjectsByStudentId(Long id);
    void deleteRegisteredSubjectOfStudent(Long studentId, Long subjectId);
}
