package com.ssafy.chaintract.domain.dto;

import com.ssafy.chaintract.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ApiModel(value = "계약증명 요청", description = "계약증명 요청의 상세 정보")
public class ContractDto {
    @ApiModelProperty(value = "계약서의 PK", example = "5197")
    private long id;
    @ApiModelProperty(value = "참가자들의 ID", example = "[8216, 0397, 818]")
    private List<String> participantEmails;
//    @ApiModelProperty(value = "생성자의 ID", example = "8216")
//    private String creatorEmail;
    @ApiModelProperty(value = "계약명", example = "삼성 청년 SW 아카데미 3월 교육지원금 서명서 - 광주 1반")
    private String name;
    @ApiModelProperty(value = "생성된 일시", example = "2022-03-30T01:48:24.044+0000")
    private Date createdDate;
    @ApiModelProperty(value = "성립된 일시", example = "2022-04-02T01:48:24.044+0000")
    private Date establishedDate;
    @ApiModelProperty(value = "블록체인 네트워크에 업로드된 일시", example = "2022-04-02T02:48:24.044+0000")
    private Date uploadedDate;
    // TODO: 절대경로를 보여주지 않고 파일명만 갖도록
    @ApiModelProperty(value = "계약서 파일의 경로", example = "/home/ubuntu/file/contract.pdf")
    private String filePath;
    @ApiModelProperty(value = "계약서의 블록체인 트랜젝션 해쉬값들", example = "[0x13..., 0x25..., ...]")
    private List<String> txHashes;
}
