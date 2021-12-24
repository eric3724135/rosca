package com.eric.rosca.domain;

import com.eric.rosca.common.Constant;
import lombok.Data;

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

    private ProductSet parentProductSet;

    private String id;

    private String name;

    private int round = Constant.ASSOCIATIONS_IN_PRODUCT;

//    @Deprecated
//    private int profitPool = 0;

    private List<Association> associationList = new ArrayList<>();

    private Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Product build(String id, String name, List<Member> members) {
        Product product = new Product(id, name);
        product.setAssociationList(buildAssociationList(members));
        return product;
    }

    private static List<Association> buildAssociationList(List<Member> members) {
        List<Association> associationList = new ArrayList<>();
        int count = 1;
        if (members.size() != Constant.JOIN_LIMIT) {
            //暫時限定只固定五人參加
            throw new IllegalArgumentException("此項產品餐與人數有異，限定5人");
        }
        for (Member member : members) {
            for (int i = 0; i < Constant.JOIN_LIMIT; i++) {
                Association association = new Association(i, member);
                associationList.add(association);
            }
        }
        return associationList;
    }

    public void payForEachRound() {

        for (Association association : associationList) {
            int charge = association.payCharge();
//            profitPool += charge;
        }
    }

    public Association getThisRoundWinner() {
        int winnerIndex = (int) (Math.random() * round+1);
        return associationList.remove(winnerIndex);
    }

    public void endThisRound(){
        round--;
    }
}
