package com.trungtd.domain;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectStatistics {
    private Subject subject;
    private Long numberOfStudentsRegistered;
}
