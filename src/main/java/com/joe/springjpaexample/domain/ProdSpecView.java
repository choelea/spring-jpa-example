package com.joe.springjpaexample.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdSpecView {

    private String code;
    
    private String value;

    private String name;

    private String not;
}
