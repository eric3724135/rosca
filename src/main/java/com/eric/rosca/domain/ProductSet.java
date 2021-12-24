package com.eric.rosca.domain;

import com.eric.rosca.common.Constant;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 產品組合
 * 規定每次必須一個產品組合為單位投資
 * 產品組合為3個產品/組
 * 每個產品最少須5個人(每人購買5個會期/產品)
 * ps.一個產品組合=每個會員投入15個會期
 */
@Data
public class ProductSet {

    private String id;
    private List<Product> products = new ArrayList<>();

    private ProductSet() {
    }

    public static List<ProductSet> initProductSet(List<Member> members) throws InterruptedException {
        List<ProductSet> resultSet = new ArrayList<>();
        if (members == null || members.size() <= Constant.JOIN_LIMIT) {
            throw new IllegalArgumentException("會員人數須大於5人");
        }
        int sets = members.size() / Constant.JOIN_LIMIT;
        for (int i = 0; i < sets; i++) {
            Member member1 = members.get(Constant.JOIN_LIMIT * i);
            Member member2 = members.get(Constant.JOIN_LIMIT * i + 1);
            Member member3 = members.get(Constant.JOIN_LIMIT * i + 2);
            Member member4 = members.get(Constant.JOIN_LIMIT * i + 3);
            Member member5 = members.get(Constant.JOIN_LIMIT * i + 4);
            List<Member> joinedMember = new ArrayList<>(Arrays.asList(member1, member2, member3, member4, member5));
            //TODO 檢查資金是否充足
            ProductSet set1 = ProductSet.build(joinedMember);
            ProductSet set2 = ProductSet.build(joinedMember);
            resultSet.add(set1);
            resultSet.add(set2);
        }
        return resultSet;
    }


    public static ProductSet reNewProductSet() {
        //TODO
        return null;
    }


    private static ProductSet build(List<Member> joinedMember) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        ProductSet set = new ProductSet();
        String id = "";
        for (int i = 0; i < Constant.PRODUCTS_IN_PRODUCT_SET; i++) {
            id = sdf.format(new Date());
            set.getProducts().add(Product.build(id, "", joinedMember));
            Thread.sleep(1000);
        }

        id = sdf.format(new Date());
        set.setId(id);
        joinedMember.stream().forEach(member -> member.getUnFullWithdrawalSets().put(set.getId(), set));
        return set;
    }

    public int getQuotaByMember(Member member) {
        int count = 0;
        for (Product product : products) {
            for (Association association : product.getAssociationList()) {
                if (association.getMember().getId() == member.getId()) {
                    count++;
                }
            }
        }
        return count;
    }

}
