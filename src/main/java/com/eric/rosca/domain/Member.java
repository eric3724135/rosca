package com.eric.rosca.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 會員
 */
public class Member {

    private int id;

    private String name;

    /** 預設資金 600w*/
    private BigDecimal funds = new BigDecimal(6000000);

    private Map<String, List<Association>> joinedAssoiations = new HashMap<>();

}
