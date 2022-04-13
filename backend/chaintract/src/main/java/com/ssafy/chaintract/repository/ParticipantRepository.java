package com.ssafy.chaintract.repository;

import com.ssafy.chaintract.domain.Contract;
import com.ssafy.chaintract.domain.Participant;
import com.ssafy.chaintract.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
//    Participant save(Participant participant);
//    List<Participant> saveAll(List<Participant> participants);
    List<Participant> findAllByContract(Contract contract);
    List<Participant> findAllByUser(User user);
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("UPDATE participant p " +
            "SET p.isSigned = CASE " +
            "WHEN p.isSigned != true THEN true ELSE false END " +
            "WHERE p.contract.id = :contractId " +
            "AND p.user.id = :userId")
    int toggleSigning(@Param("contractId") Long contractId, @Param("userId") Long userId);
    boolean existsByContractAndIsSigned(Contract contract, boolean b);
}
