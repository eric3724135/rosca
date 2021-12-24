package com.eric.rosca.domain;

import com.eric.rosca.common.Constant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 互助會每個會期
 */
@Data
public class Association {
    /**
     * 產品中序號
     */
    private int productIndex;
    /**
     * 投入會員
     */
    private Member member;
    /**
     * 投入費用
     */
    private int charge = Constant.CHARGE;
    /**
     * member投入次數
     */
    private int fundCount = 0;
    /**
     * member投入金額
     */
    private int fundPool = 0;
    /**
     * 累積收取管理費
     */
    private int fees = 0;
    /**
     * 累積獲利獎金
     */
    private int profitPool = 0;


    /**
     * 管理費用
     */
    private int managementFee = Constant.MANAGEMENT_FEE;

    public Association(int productIndex, Member member) {
        this.productIndex = productIndex;
        this.member = member;
    }

    public int payCharge() {
        member.setFunds(member.getFunds() - charge);
        profitPool += Constant.PROFIT;
        fees += Constant.MANAGEMENT_FEE;
        fundPool += charge;
        fundCount++;
        return charge;
    }

    /**
     * 當得標者處理本次得標金額
     */
    public void processWithdrawal(ProductSet productSet) {
        ProductSet existSet = member.getUnFullWithdrawalSets().get(productSet.getId());
        //此產品組合是不是已經全額領取
        boolean isAlreadyFullWithdrawal = existSet == null;
        if (fundCount < Constant.ASSOCIATIONS_IN_PRODUCT / 2) {
            //前期抽中
            if (isAlreadyFullWithdrawal) {
                //進行結算
                processNormalWithdrawal();
            } else {
                //TODO 確認一下 產品組合中剩多少份額 如果剩最後一個必須全額領取
                int quota = productSet.getQuotaByMember(member);
                if(quota==1){
                    processFullWithdrawal();
                }
            }
        } else {
            //後期抽中
            //TODO要知道是哪時要知道是哪時
            if (isAlreadyFullWithdrawal) {
                //進行結算
                processNormalWithdrawal();
            } else {
                //TODO
            }
        }
    }

    private void processNormalWithdrawal() {
        int finalProfit = fundPool - fees + profitPool;
        member.addFunds(finalProfit);

    }

    private void processFullWithdrawal() {

    }


}
