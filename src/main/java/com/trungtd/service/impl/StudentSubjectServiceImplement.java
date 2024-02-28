package com.trungtd.service.impl;

import com.trungtd.custom.exception.NotFoundException;
import com.trungtd.domain.Student;
import com.trungtd.domain.StudentSubject;
import com.trungtd.domain.Subject;
import com.trungtd.repository.StudentRepository;
import com.trungtd.repository.StudentSubjectRepository;
import com.trungtd.repository.SubjectRepository;
import com.trungtd.service.StudentSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class StudentSubjectServiceImplement implements StudentSubjectService {
    private final StudentSubjectRepository studentSubjectRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public StudentSubjectServiceImplement(StudentSubjectRepository studentSubjectRepository,
                                          StudentRepository studentRepository,
                                          SubjectRepository subjectRepository) {
        this.studentSubjectRepository = studentSubjectRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public StudentSubject registerStudentForSubject(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Không tìm thấy sinh viên với ID: " + studentId)
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Không tìm thấy môn học với ID: " + subjectId)
        );
        //Kiểm tra xem học sinh đã đăng ký môn học chưa?
        if(student.getSubjectSet().contains(subject)) {
            throw new IllegalArgumentException("Sinh viên đã đăng ký môn học này");
        }
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(student);
        studentSubject.setSubject(subject);

//        student.getSubjectList().add(subject);
//        studentRepository.save(student);

        return studentSubjectRepository.save(studentSubject);  //"student_subject"
    }

    @Override
    public Set<Subject> getSubjectsByStudentId(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Không tìm thấy sinh viên với ID: " + id)
        );
        Set<Subject> subjectList = student.getSubjectSet();
        if(!subjectList.isEmpty())
            return subjectList;
        else
            throw new IllegalArgumentException("Sinh viên chưa đăng ký môn học nào");
    }

    @Override
    public void deleteRegisteredSubjectOfStudent(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Không tìm thấy sinh viên với ID: " + studentId)
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Môn học cần xóa của sinh viên có ID: " + studentId + "không tồn tại.")
        );

        StudentSubject studentSubject = null;
        studentSubject = studentSubjectRepository.findByStudentAndSubject(student, subject);
        System.out.println(studentSubject);
        if(studentSubject == null)
            throw new NotFoundException("Sinh viên chưa đăng ký môn học này");
        studentSubjectRepository.delete(studentSubject);
    }


}