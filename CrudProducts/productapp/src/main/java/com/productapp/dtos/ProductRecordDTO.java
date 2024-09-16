package com.productapp.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRecordDTO(
    
    @NotBlank(message = "The product name must not be blank") 
    @NotNull(message = "The product name must not be null")
    String name, 
    
    @NotNull(message = "The product value must not be null") 
    @Min(value = 1, message = "The price must be greater than 0")
    BigDecimal valueProduct) {

}
