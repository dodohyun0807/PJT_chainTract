package com.ssafy.chaintract.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "participant")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id @GeneratedValue
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    boolean isSigned;

}
