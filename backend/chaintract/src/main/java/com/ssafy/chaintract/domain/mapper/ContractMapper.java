package com.ssafy.chaintract.domain.mapper;

import com.ssafy.chaintract.domain.Contract;
import com.ssafy.chaintract.domain.Participant;
import com.ssafy.chaintract.domain.dto.ContractDto;
import com.ssafy.chaintract.repository.ParticipantRepository;
import com.ssafy.chaintract.repository.TransactionRepository;
import com.ssafy.chaintract.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContractMapper {
    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public ContractDto toDto(Contract contract) {
        return ContractDto.builder()
                .id(contract.getId())
                .participantEmails(participantRepository.findAllByContract(contract).stream().map(p -> p.getUser().getEmail()).collect(Collectors.toList()))
//                .creatorEmail(contract.getCreator().getEmail())
                .name(contract.getName())
                .createdDate(contract.getCreatedDate())
                .establishedDate(contract.getEstDate())
                .uploadedDate(contract.getUploadedDate())
                .filePath(contract.getFilePath())
                .txHashes(transactionRepository.findAllByContract(contract)
                        .stream().map(t -> t.getTxHash()).collect(Collectors.toList()))
                .build();
    }

    public Contract toEntity(ContractDto contractDto) {
        return Contract.builder()
                .id(contractDto.getId())
//                .creator(userRepository.findUserByEmail(contractDto.getCreatorEmail()).get(0))
                .name(contractDto.getName())
                .createdDate(contractDto.getCreatedDate())
                .estDate(contractDto.getEstablishedDate())
                .uploadedDate(contractDto.getUploadedDate())
                .filePath(contractDto.getFilePath())
                .build();
    }
}
