package com.smart.program.request.order;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO2 {
    private Long id;
    private Integer num;
    private BigDecimal price;
    private String name;
    private String memo;
}
