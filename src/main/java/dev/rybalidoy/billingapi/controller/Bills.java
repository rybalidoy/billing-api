package dev.rybalidoy.billingapi.controller;

import dev.rybalidoy.billingapi.entity.Bill;
import dev.rybalidoy.billingapi.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
public class Bills {

    @Autowired
    private BillService billService;

    /**
     *  Default
     */
    @GetMapping
    public ResponseEntity<List<Bill>> index() {
        return new ResponseEntity<List<Bill>>(billService.fetchAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bill>> getBillTransactionById(@PathVariable Integer id) {
        return new ResponseEntity<Optional<Bill>>(billService.findTransactionById(id),HttpStatus.OK);
    }

    @GetMapping("/billingcycle={value}")
    public ResponseEntity<Optional<List<Bill>>> getBillTransactionsByBillingCycle(@PathVariable int value) {
        return new ResponseEntity<Optional<List<Bill>>>(billService.findBillsByBillingCycle(value),HttpStatus.OK);
    }
}
