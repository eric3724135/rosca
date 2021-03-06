package com.eric.rosca.domain;

import com.eric.rosca.common.WithdrawalMode;
import lombok.Data;

import java.util.*;

/**
 * 會員
 */
@Data
public class Member {
    /**
     * 預設資金 600w
     */
    private static final int INIT_FUNDS = 6000000;

    private int id;

    private String name;

    private WithdrawalMode withdrawalMode = WithdrawalMode.FULL_Withdrawal_AT_FIRST;
    /**
     * 預設資金 600w
     */
    private int funds = INIT_FUNDS;

    private Map<String, List<Association>> joinedAssociations = new HashMap<>();

    private List<Product> joinedProducts = new ArrayList<>();

    /**
     * 加入但未全額領取的產品組合
     */
    private Map<String, ProductSet> unFullWithdrawalSets = new HashMap<>();

    /**
     * 加入且已全額領取的產品組合
     */
    private Map<String, ProductSet> fullWithdrawalSets = new HashMap<>();

    public void setFullWithdrawal(ProductSet set) {
        ProductSet fullWithdrawalSet = unFullWithdrawalSets.get(set.getId());
        fullWithdrawalSets.put(fullWithdrawalSet.getId(), fullWithdrawalSet);
    }

    public void addFunds(int updateFunds) {
        funds += updateFunds;
    }

}
