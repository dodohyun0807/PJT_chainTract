package com.ssafy.chaintract.repository;

import com.ssafy.chaintract.domain.Contract;
import com.ssafy.chaintract.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
//    @Query("select c from contract c, participant p " +
//            "where c.id = p.contract.id and p.user.id = :user.id")
//    List<Contract> findAllByUser(User user);
    Optional<Contract> findById(Long contractId);
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("update contract c " +
            "set c.estDate = :date " +
            "where c.id = :contractId")
    int completeContract(long contractId, Date date);
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("update contract c " +
            "set c.uploadedDate = :date " +
            "where c.id = :contractId")
    int uploadContract(long contractId, Date date);
//    @Query("select c from contract c, participant p " +
//            "where c.id = p.contract.id " +
//            "AND p.user.id = :userId " +
//            "WHEN :isEstablished = TRUE THEN c.estDate is not null " +
//            "ELSE c.estDate is null END " +
//            "AND c.estDate is not null = :isEstablished " +
//            "AND p.isSigned = :isSigned")
//    Optional<List<Contract>> getContracts(long userId, boolean isEstablished, boolean isSigned);
    @Query("select c from contract c, participant p " +
            "where c.id = p.contract.id " +
            "AND p.user.id = :userId " +
            "AND p.isSigned = false")
    Optional<List<Contract>> getContractsINotSigned(long userId);
    @Query("select c from contract c, participant p " +
            "where c.id = p.contract.id " +
            "AND p.user.id = :userId " +
            "AND c.estDate is not null " +
            "AND c.uploadedDate is null")
    Optional<List<Contract>> getEstablishedContracts(long userId);
    @Query("SELECT c " +
            "FROM contract c, participant p " +
            "where c.id = p.contract.id " +
            "AND p.user.id = :userId " +
            "AND c.estDate is null " +
            "AND p.isSigned = true")
    Optional<List<Contract>> getContractsOthersNotSigned(long userId);
    @Query("SELECT c " +
            "FROM contract c, participant p " +
            "where c.id = p.contract.id " +
            "AND p.user.id = :userId " +
            "AND c.uploadedDate is not null")
    Optional<List<Contract>> getContractsUploaded(long userId);
}
