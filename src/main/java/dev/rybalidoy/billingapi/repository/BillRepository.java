package dev.rybalidoy.billingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.rybalidoy.billingapi.entity.Bill;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill,Integer> {
    Optional<Bill> findTransactionById(Integer id);
    Optional<List<Bill>> findBillsByBillingCycle(int value);
}
