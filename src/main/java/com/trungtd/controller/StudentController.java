package com.trungtd.controller;

import com.trungtd.domain.Student;
import com.trungtd.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("")
    public ResponseEntity<?> getAllStudent() {
        List<Student> studentList = studentService.getAllStudents();
        return ResponseEntity.ok(studentList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchStudent(@RequestParam(required = false, defaultValue = "") String name) {
        List<Student> searchedStudentList = studentService.searchStudentByName(name);
        return ResponseEntity.ok(searchedStudentList);
    }
    @PostMapping("/add-student")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }
    @PutMapping("/update-student/{id}")
    public ResponseEntity<?> updateInfoStudent(@PathVariable Long id, @RequestBody Student student) {

        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }
    @DeleteMapping("/delete-stu/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}