package com.elyasi.util.shared_home_finance.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainTableDataModel {
    private int no;
    private BigDecimal price;
    private BigDecimal amir;
    private BigDecimal raouf;
    private BigDecimal hirbod;
}
