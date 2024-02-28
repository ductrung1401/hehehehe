package com.trungtd.service.impl;

import com.trungtd.custom.exception.NotFoundException;
import com.trungtd.domain.Student;
import com.trungtd.domain.Subject;
import com.trungtd.domain.SubjectStatistics;
import com.trungtd.repository.StudentRepository;
import com.trungtd.repository.StudentSubjectRepository;
import com.trungtd.repository.SubjectRepository;
import com.trungtd.service.FunctionsBetweenStuAndSub;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunctionsBetweenStuAndSubImplement implements FunctionsBetweenStuAndSub {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public FunctionsBetweenStuAndSubImplement(StudentRepository studentRepository,
                                              SubjectRepository subjectRepository,
                                              EntityManager entityManager) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Student registerSubForStu(Long studentId, Long subjectId) {
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
        student.getSubjectSet().add(subject);

        return studentRepository.save(student);
    }

    @Override
    public Student deleteRegisteredSubOfStu(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Không tìm thấy sinh viên với ID: " + studentId)
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Môn học cần xóa không tồn tại.")
        );
        if(!student.getSubjectSet().contains(subject)) {
            throw new IllegalArgumentException("Môn học cần xóa này chưa được sinh viên đăng ký.");
        }
        student.getSubjectSet().remove(subject);
        return studentRepository.save(student);
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
    public List<Student> getStudentsBySizeSubjectSet(Long size) {
        List<Student> studentList = studentRepository.findStudentsBySubjectSetSize(size);
        if (!studentList.isEmpty())
            return studentList;
        throw new NotFoundException("Không có sinh viên nào đăng ký " + size + " môn học");
    }

    @Override
    public Set<Student> getStudentsBySubjectId(Long subId) {
        Subject subject = subjectRepository.findById(subId).orElseThrow(
                () -> new NotFoundException("Không tìm thấy subject với ID: " + subId)
        );
        Set<Student> students = studentRepository.findBySubjectSetContaining(subject);
        if(!students.isEmpty())
            return students;
        else
            throw new IllegalArgumentException("Môn học này chưa có học sinh nào đăng ký");
    }
    @Autowired
    private  StudentSubjectRepository studentSubjectRepository;
    @Override
    public List<SubjectStatistics> getMostPopularSubject(int quantity) {
        Pageable pageable = PageRequest.of(0, quantity);
        Page<StudentSubjectRepository.MostRegisterSubject>
                mostRegisterSubjects = studentSubjectRepository.findMostRegisteredSubjects(pageable);

//        List<Long> subjectIds = mostRegisterSubjects.stream().map(StudentSubjectRepository.MostRegisterSubject::getSubjectId)
//                .collect(Collectors.toList());
//        System.out.println(subjectIds);
//
//        Map<Long, Long> subjectIdToNumOfStus = mostRegisterSubjects.stream()
//                .collect(Collectors.toMap(StudentSubjectRepository.MostRegisterSubject::getSubjectId,
//                        StudentSubjectRepository.MostRegisterSubject::getNumOfStu,
//                        (existingValue, newValue) -> existingValue));
//        System.out.println(subjectIdToNumOfStus);
//
//        for (Long subjectId : subjectIds) {
//            Subject subject = subjectRepository.findById(subjectId)
//                    .orElseThrow(() -> new NotFoundException("Subject not found with ID: " + subjectId));
//            Long numOfStu = subjectIdToNumOfStus.get(subjectId);
//            subjectStatistics.add(new SubjectStatistics(subject, numOfStu));
//        }
//        return subjectStatistics;
        List<SubjectStatistics> subjectStatistics = mostRegisterSubjects.getContent().stream()
                .map(ms -> new SubjectStatistics(
                        subjectRepository.findById(ms.getSubjectId()).orElse(null),
                        ms.getNumOfStu()
                ))
                .collect(Collectors.toList());

        return subjectStatistics;
    }
}
