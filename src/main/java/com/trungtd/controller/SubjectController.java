package com.trungtd.controller;

import com.trungtd.domain.Subject;
import com.trungtd.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("")
    public ResponseEntity<?> getAllSubjects() {
        List<Subject> subjectList = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjectList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        Subject subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @PostMapping("/add-sub")
    public ResponseEntity<?> addSubject(@RequestBody Subject subject) {
        return ResponseEntity.status(200).body(subjectService.addSubject(subject));
    }

    @PutMapping("/update-sub/{id}")
    public ResponseEntity<?> updateInforSubject(@PathVariable Long id, @RequestBody Subject subject) {
        Subject updatedSubject = subjectService.updateSubject(id, subject);
        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/delete-sub/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }
}
