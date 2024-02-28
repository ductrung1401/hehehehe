package com.trungtd.service.impl;

import com.trungtd.custom.exception.NotFoundException;
import com.trungtd.domain.Subject;
import com.trungtd.repository.SubjectRepository;
import com.trungtd.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImplement implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> allSubject = subjectRepository.findAll();
        if(!allSubject.isEmpty()) {
            return allSubject;
        } else {
            throw new NotFoundException("Không tồn tại môn học nào trong hệ thống!");
        }
    }

    @Override
    public Subject getSubjectById(Long id) {
//        if(subjectRepository.findById(id).isPresent()) {
//            Subject subject = subjectRepository.findById(id).get();
//            return subject;
//        } else {
//            throw new NotFoundException("Không tìm thấy môn học với id: " + id);
//        }
        if(subjectRepository.existsById(id)) {
            Subject subject = subjectRepository.findById(id).get();
            return subject;
        } else {
            throw new NotFoundException("Không tìm thấy môn học với id=" + id);
        }
    }

    @Override
    public Subject addSubject(Subject subject) {
        if(subject != null)
            return subjectRepository.save(subject);
        else
            return null;
    }

    @Override
    public Subject updateSubject(Long id, Subject subject) {
        Subject subjectNeedUpdate = subjectRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Môn học cần cập nhật hiện không tồn tại trong hệ thống")
        );
        subjectNeedUpdate.setName(subject.getName() == null ? subjectNeedUpdate.getName() : subject.getName());
        subjectNeedUpdate.setNoC(subject.getNoC() == null ? subjectNeedUpdate.getNoC() : subject.getNoC());
        subjectNeedUpdate.setSubCode(subject.getSubCode() == null ? subjectNeedUpdate.getSubCode() : subject.getSubCode());
        subjectRepository.save(subjectNeedUpdate);
        return subjectNeedUpdate;
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subjectToDelete = subjectRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Môn học cần xóa với id=" + id + " không có trong hệ thống")
        );
        subjectRepository.delete(subjectToDelete);
    }
}