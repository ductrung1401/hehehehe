package com.trungtd.service.impl;

import com.trungtd.custom.exception.NotFoundException;
import com.trungtd.domain.Student;
import com.trungtd.repository.StudentRepository;
import com.trungtd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImplement implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        List<Student> allStudents = studentRepository.findAll();
        if(!allStudents.isEmpty()) {
            studentList.addAll(allStudents);
            return studentList;
        } else {
            throw new NotFoundException("Không tồn tại học sinh nào trong hệ thống!");
        }

    }
    @Override
    public Student getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Không tìm thấy học sinh với id=" + id)
        );
        return student;
    }
    @Override
    public List<Student> searchStudentByName(String name) {
        List<Student> rs = new ArrayList<>();
        List<Student> allStudents = studentRepository.findAll();

        if(!allStudents.isEmpty()) {
            for (Student student : allStudents) {
                if(student.getName().contains(name)) {
                    rs.add(student);
                }
            }
            if(!rs.isEmpty())
                return rs;
            else
                throw new NotFoundException("Không tồn tại học sinh tên " + name + " trong hệ thống!");
        } else {
            throw new NotFoundException("Không tồn tại học sinh nào trong hệ thống!");
        }
    }
    @Override
    public Student addStudent(Student student) {
        if(student != null)
            return studentRepository.save(student);
        else
            return null;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student studentNeedUpdate = studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Học sinh với id=" + id + " cần cập nhật thông tin hiện đang không tồn tại trong hệ thống")
        );
        studentNeedUpdate.setAddress(student.getAddress() != null ? student.getAddress() : studentNeedUpdate.getAddress());
        studentNeedUpdate.setName(student.getName() != null ? student.getName() : studentNeedUpdate.getName());
        studentNeedUpdate.setGender(student.getGender() != null ? student.getGender() : studentNeedUpdate.getGender());
        studentNeedUpdate.setEmail(student.getEmail() != null ? student.getEmail() : studentNeedUpdate.getEmail());
        studentNeedUpdate.setDoB(student.getDoB() != null ? student.getDoB() : studentNeedUpdate.getDoB());
        studentRepository.save(studentNeedUpdate);
        return studentNeedUpdate;
    }

    @Override
    public void deleteStudent(Long id) {
        Student studentToDelete = studentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Học sinh cần xóa với id=" + id + " không có trong hệ thống")
        );
        studentRepository.delete(studentToDelete);
    }
}