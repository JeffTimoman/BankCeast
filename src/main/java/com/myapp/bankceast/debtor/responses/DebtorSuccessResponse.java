package com.myapp.bankceast.debtor.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Builder
public class DebtorSuccessResponse<T> {
    private int httpStatus;
    private String message;
    private ZonedDateTime timestamp;
    private T data;
}
