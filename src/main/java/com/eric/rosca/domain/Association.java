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
     * 預先支付管理費資金池
     */
    private int feePool = Constant.PRE_PAID_MANAGEMENT_FEE;
    /**
     * 累積獲利獎金
     */
    private int profitPool = 0;

    /**
     * 管理費用
     */
    private int managementFee = Constant.MANAGEMENT_FEE;

    private boolean fullWithdrawal = false;

    public Association(int productIndex, Member member) {
        this.productIndex = productIndex;
        this.member = member;
        //預付管理費
        member.addFunds(Constant.PRE_PAID_MANAGEMENT_FEE);
    }

    public int payCharge() {
        member.setFunds(member.getFunds() - charge);
        profitPool += Constant.PROFIT;
        fees += Constant.MANAGEMENT_FEE;
        fundPool += charge;
        fundCount++;
        return charge;
    }

    public int payFullWithdrawalCharge() {
        //TODO 全額領取後每會期要付出得金額
        int bill = Constant.CHARGE + Constant.PROFIT + Constant.MANAGEMENT_FEE;
        member.setFunds(member.getFunds() - bill);
        return bill;
    }

    /**
     * 當得標者處理本次得標金額
     */
    public void processWithdrawal(ProductSet productSet) {
        ProductSet existSet = member.getUnFullWithdrawalSets().get(productSet.getId());
        //此產品組合是不是已經全額領取
        boolean isAlreadyFullWithdrawal = existSet == null;
        int currentRound = fundCount + 1;
        if (isAlreadyFullWithdrawal) {
            //進行結算
            this.processNormalWithdrawal();
        } else {
            if (currentRound >= member.getWithdrawalMode().getPreferRound()) {
                //滿足全額領取條件
                this.processFullWithdrawal();
            } else {
                //如果剩最後一個必須全額領取
                int quota = productSet.getQuotaByMember(member);
                if (quota == 1) {
                    //僅剩最後一個份額必須執行全額領取
                    this.processFullWithdrawal();
                    fullWithdrawal = true;
                } else {
                    //尚有份額且條件未到 執行一班領取
                    this.processNormalWithdrawal();
                }
            }

        }
    }

    private void processNormalWithdrawal() {
        //TODO
        int finalProfit = fundPool + feePool - fees + profitPool;
        member.addFunds(finalProfit);

    }

    private void processFullWithdrawal() {
        //全額取回 但接下來每期都要繳交費用＆利息＆管理費
        int finalProfit = fundPool + feePool - fees + profitPool + charge * (25 - fundCount);
        member.addFunds(finalProfit);

    }


}
