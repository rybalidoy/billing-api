package dev.rybalidoy.billingapi.service;

import dev.rybalidoy.billingapi.entity.Bill;
import dev.rybalidoy.billingapi.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    private List<Bill> fetchAll() {
        return billRepository.findAll();
    }

    private void insertList(List<Bill> bills) {
        billRepository.saveAll(bills);
    }

    public Optional<Bill> findTransactionById(Integer id) {
        return billRepository.findTransactionById(id);
    }

    public Optional<List<Bill>> findBillsByBillingCycle(int value) {
        return billRepository.findBillsByBillingCycle(value);
    }
}
