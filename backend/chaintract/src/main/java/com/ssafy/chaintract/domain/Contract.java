package com.ssafy.chaintract.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "contract")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Contract {
    @Id @GeneratedValue
    @Column(name = "contract_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User creator;

    private String name;

    @CreatedDate
    private Date createdDate;
    private Date estDate;
    private Date uploadedDate;

    private String filePath;


}
