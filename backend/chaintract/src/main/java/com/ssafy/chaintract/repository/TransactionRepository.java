package com.ssafy.chaintract.repository;

import com.ssafy.chaintract.domain.Contract;
import com.ssafy.chaintract.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByContract(Contract contract);
}
