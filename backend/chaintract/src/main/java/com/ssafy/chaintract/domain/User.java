package com.ssafy.chaintract.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

//    @OneToMany(mappedBy = "user")
//    private List<Contract> contracts = new ArrayList<>();

    private String access_token;
    private String name;
    private String email;
    private String file_path;
    private boolean is_admin;

    @Column(nullable = false)
    private String socialId;

    public User() {

    }

    // 연관관계 메서드
    public void setUserInfo(String name, String email){
        this.name = name;
        this.email = email;

    }

}
