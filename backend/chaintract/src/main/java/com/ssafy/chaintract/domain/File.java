package com.ssafy.chaintract.domain;

import javax.persistence.*;

@Entity
public class File {
    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    private String filePath;
}
