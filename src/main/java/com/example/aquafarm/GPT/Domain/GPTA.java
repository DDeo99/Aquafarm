package com.example.aquafarm.GPT.Domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "GPT_Q")
public class GPTA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QPTA_ID")
    private int qptaId;

    @Column(name = "Answer_Date")
    private LocalDateTime answerDate;

    @Column(name = "Output", columnDefinition = "TEXT")
    private String output;

    @ManyToOne
    @JoinColumn(name = "QPTQ_ID")
    private GPTQ gptq;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

