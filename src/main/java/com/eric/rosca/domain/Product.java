package com.eric.rosca.domain;

import com.eric.rosca.common.Constant;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每個產品共有25個會期
 * 一人可以參與5個會期/產品
 */
@Data
public class Product {


    private String id;

    private String name;

    private BigDecimal profitPool = BigDecimal.ZERO;

    private Map<Integer, Association> associationMap = new HashMap<>();


    public static Product build(String id, String name, List<Member> members) {
        Product product = new Product(id, name);
        product.setAssociationMap(buildAssociationMap(members));
        return product;
    }

    private static Map<Integer, Association> buildAssociationMap(List<Member> members) {
        Map<Integer, Association> associationMap = new HashMap<>();
        int count = 1;
        if (members.size() != Constant.JOIN_LIMIT) {
            //暫時限定只固定五人參加
            throw new IllegalArgumentException("此項產品餐與人數有異，限定5人");
        }
        for (Member member : members) {
            for (int i = 0; i < Constant.JOIN_LIMIT; i++) {
                Association association = new Association(member);
                associationMap.put(count++, association);
            }
        }
        return associationMap;
    }

    private Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
