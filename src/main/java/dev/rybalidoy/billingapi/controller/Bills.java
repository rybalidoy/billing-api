package dev.rybalidoy.billingapi.controller;

import dev.rybalidoy.billingapi.entity.Bill;
import dev.rybalidoy.billingapi.helper.FileHelper;
import dev.rybalidoy.billingapi.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
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

    @ResponseBody
    @RequestMapping(value = "/add/file", method = RequestMethod.POST)
    public ResponseEntity<List<Bill>> addListOfTransactions(@RequestParam("file") MultipartFile file) throws IOException {
        FileHelper helper = new FileHelper();
        List<Bill> result = helper.readFile(file);
        try {
            // Add to database
            billService.insertList(result);
        }
        catch(MultipartException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<List<Bill>>(result,HttpStatus.OK);
    }
}
