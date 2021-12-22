package com.eric.rosca.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 會員
 */
@Data
public class Member {
    /** 預設資金 600w*/
    private static final BigDecimal INIT_FUNDS = new BigDecimal(6000000);

    private int id;

    private String name;

    /** 預設資金 600w*/
    private BigDecimal funds = INIT_FUNDS;

    private Map<String, List<Association>> joinedAssociations = new HashMap<>();

    private List<Product> joinedProducts = new ArrayList<>();

}
