package com.ssafy.chaintract.service;

import com.ssafy.chaintract.domain.Contract;
import com.ssafy.chaintract.domain.Participant;
import com.ssafy.chaintract.domain.User;
import com.ssafy.chaintract.domain.dto.ContractDto;
import com.ssafy.chaintract.domain.mapper.ContractMapper;
import com.ssafy.chaintract.file.Encryption;
import com.ssafy.chaintract.file.FileStore;
import com.ssafy.chaintract.file.UploadFile;
import com.ssafy.chaintract.repository.ContractRepository;
import com.ssafy.chaintract.repository.ParticipantRepository;
import com.ssafy.chaintract.repository.UserRepository;
import com.ssafy.chaintract.smartcontract.SmartContractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Slf4j
@Service
public class ContractService {
    @Autowired
    SmartContractService smartContractService;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    FileStore fileStore;

    @Transactional
    public ContractDto createContract(ContractDto contractDto, String creatorEmail) {
        Contract contract = contractRepository.save(contractMapper.toEntity(contractDto));

        List<Participant> participants = new ArrayList<>();
        for(String email : contractDto.getParticipantEmails()) {
            User user = userRepository.findUserByEmail(email).get(0);
            Participant participant = Participant.builder()
                    .contract(contract)
                    .user(user)
                    .isSigned(user.getEmail().equals(creatorEmail))
                    .build();
            participants.add(participant);
        }
        participantRepository.saveAll(participants);

        return contractMapper.toDto(contract);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(file);
        log.info("full path: {}", uploadFile.getFullPath());
        return uploadFile.getFullPath();
    }

    public byte[] downloadFile(long contractId) throws IOException {
        String fullPath = contractRepository.findById(contractId).get().getFilePath();
        byte[] file = fileStore.retrieveFile(fullPath);
        return file;
    }

    @Transactional
    public void toggleSignature(long contractId, User user) throws Exception {
        Contract contract = contractRepository.findById(contractId).get();
        participantRepository.toggleSigning(contractId, user.getId());
        if(participantRepository.existsByContractAndIsSigned(contract, false) == false) {
            completeContract(contractId);
        }
    }

    @Transactional
    public void completeContract(long contractId) throws Exception {
        Date date = new Date();
        contractRepository.completeContract(contractId, date);
    }

    @Transactional
    public void uploadContract(long contractId) throws Exception {
        Contract contract = contractRepository.findById(contractId).get();
        byte[] plainByte = Encryption.getBytes(new File(contract.getFilePath()));
        String plainBase64 = new String(plainByte, "UTF-8");
        String enc = Encryption.encrypt(plainByte);
        String dec = Encryption.decrypt(enc);

        if(plainBase64.equals(dec)) {
            if(smartContractService.uploadContract(contract.getId(), enc) == false) {
                // TODO 업로드 중 중단된 경우 총 몇 tx를 보내야하며 몇 개의 tx가 올라갔는지 표시
                return;
            }
            Date date = new Date();
            contractRepository.uploadContract(contract.getId(), date);
            // TODO: 블록체인에 올리지 못한 경우를 표현하는 계약의 상태정보?
        } else {
            // TODO: 암호화 실패 익셉션
        }
    }

    public List<ContractDto> getContracts(boolean isUploaded, boolean isEstablished, boolean isSigned, User user) {
        Optional<List<Contract>> optionalContracts = null;

        if(isUploaded) {
            optionalContracts = contractRepository.getContractsUploaded(user.getId());
        } else if(isEstablished) {
            optionalContracts = contractRepository.getEstablishedContracts(user.getId());
        } else if(isSigned) {
            optionalContracts = contractRepository.getContractsOthersNotSigned(user.getId());
        } else {
            optionalContracts = contractRepository.getContractsINotSigned(user.getId());
        }

        return optionalContracts.get().stream().map(x -> contractMapper.toDto(x)).collect(Collectors.toList());
    }

    public ContractDto getContract(long contractId) {
        return contractMapper.toDto(contractRepository.findById(contractId).get());
    }

}
