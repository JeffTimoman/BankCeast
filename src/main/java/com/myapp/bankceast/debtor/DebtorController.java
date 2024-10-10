package com.myapp.bankceast.debtor;

import com.myapp.bankceast.debtor.responses.DebtorSuccessResponse;
import com.myapp.bankceast.exception.ApiBadRequestException;
import com.myapp.bankceast.models.Debtor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/debtors")
public class DebtorController {

    public final DebtorService debtorService;

    public DebtorController(DebtorService debtorService) {
        this.debtorService = debtorService;
    }

    @GetMapping("/hello")
    public String hello(){
        throw new ApiBadRequestException("Oops, parameter not correct.");
    }


    @GetMapping
    public ResponseEntity<DebtorSuccessResponse<List<Debtor>>> findAll() {
        List<Debtor> all = debtorService.findAll();
        DebtorSuccessResponse<List<Debtor>> response = DebtorSuccessResponse.<List<Debtor>>builder()
                .httpStatus(HttpStatus.OK.value())
                .message("Data Fetched Successfully")
                .data(all)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity create(@RequestParam String name, @RequestParam String email, @RequestParam String nip) {
        final Debtor debtor = debtorService.create(name, email, nip);
        DebtorSuccessResponse response = DebtorSuccessResponse.builder()
                .httpStatus(HttpStatus.CREATED.value())
                .message("Data Created Successfully.")
                .data(debtor)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).
                body(response);
    }

    @PutMapping("")
    public ResponseEntity<DebtorSuccessResponse<Debtor>> update(@RequestParam Long id,
                                                         @RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String email,
                                                         @RequestParam(required = false) String nip) {
        final Debtor debtor = debtorService.update(id, name, email, nip);

        DebtorSuccessResponse<Debtor> response = DebtorSuccessResponse.<Debtor>builder()
                .httpStatus(HttpStatus.OK.value())
                .message("Data Updated Successfully")
                .data(debtor)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
