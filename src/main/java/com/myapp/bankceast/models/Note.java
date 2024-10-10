package com.myapp.bankceast.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {
    private Long id;
    private Integer amount;
    private String receipt;
    private Long debtorId;
    private Integer status;
}
