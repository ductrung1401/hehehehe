package com.trungtd.controller;

import com.trungtd.service.FunctionsBetweenStuAndSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StuAndSubController {
    @Autowired
    private FunctionsBetweenStuAndSub functionsBetweenStuAndSub;
    @PostMapping("/register-sub")
    public ResponseEntity<?> registerSubForStu(@RequestParam Long studentId,
                                               @RequestParam Long subjectId) {
        try {
            return ResponseEntity.ok(functionsBetweenStuAndSub.registerSubForStu(studentId, subjectId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-registeredSub")
    public ResponseEntity<?> deleteRegisteredSubOfStu(@RequestParam Long studentId,
                                                      @RequestParam Long subjectId) {
        try {
            functionsBetweenStuAndSub.deleteRegisteredSubOfStu(studentId, subjectId);
            return ResponseEntity.ok("Delete the subject successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/subList-registered-byStuId/{id}")
    public ResponseEntity<?> getSubjectsByStudentId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(functionsBetweenStuAndSub.getSubjectsByStudentId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stuList-by-condition")
    public ResponseEntity<?> getStudentsByCondition(@RequestParam Long size) {

        return ResponseEntity.ok(functionsBetweenStuAndSub.getStudentsBySizeSubjectSet(size));
    }

    @GetMapping("/stuList-registeredSub-bySubId/{subId}")
    public ResponseEntity<?> getStuListRegisteredAnySubject(@PathVariable Long subId) {
        try {
            return ResponseEntity.ok(functionsBetweenStuAndSub.getStudentsBySubjectId(subId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/popular-subject")
    public ResponseEntity<?> getMostPopularSubject(@RequestParam int quantity) {
        return ResponseEntity.ok(functionsBetweenStuAndSub.getMostPopularSubject(quantity));
    }
}
