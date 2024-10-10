package com.myapp.bankceast.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Debtor {
    private Long id;
    private String name;
    private String email;
    private String nip;
}
