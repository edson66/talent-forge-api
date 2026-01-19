package com.talentForge.api.infrastructure.persistence.entity;

import com.talentForge.api.domain.model.roles.StatusJob;
import com.talentForge.api.infrastructure.web.dto.request.UpdateJobDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String requirements;

    @ManyToOne
    @JoinColumn(name = "recruiter_id",referencedColumnName = "id")
    private Recruiter recruiter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusJob statusJob = StatusJob.OPEN;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    public void update(UpdateJobDTO data) {
        if (title != null){
            this.title = data.title();
        }
        if (description != null){
            this.description = data.description();
        }
        if (requirements != null){
            this.requirements = data.requirements();
        }
    }
}
