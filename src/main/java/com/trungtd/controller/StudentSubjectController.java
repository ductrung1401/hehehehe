package com.trungtd.controller;

import com.trungtd.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StudentSubjectController {
    private final StudentSubjectService studentSubjectService;
    @Autowired
    public StudentSubjectController (StudentSubjectService studentSubjectService) {
        this.studentSubjectService = studentSubjectService;
    }
    @PostMapping("/register-subject")
    public ResponseEntity<?> registerStudentForSubject(@RequestParam Long studentId,
                                                       @RequestParam Long subjectId) {
        try {
            return ResponseEntity.ok(studentSubjectService.registerStudentForSubject(studentId, subjectId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list-registered-subjects/{id}")
    public ResponseEntity<?>  getSubjectsByStudentId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentSubjectService.getSubjectsByStudentId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/delete-subject")
    public ResponseEntity<?> deleteRegisteredSubjectOfStudent(@RequestParam Long studentId,
                                                              @RequestParam Long subjectId) {
        studentSubjectService.deleteRegisteredSubjectOfStudent(studentId, subjectId);
        return ResponseEntity.ok("Success");
    }
}
