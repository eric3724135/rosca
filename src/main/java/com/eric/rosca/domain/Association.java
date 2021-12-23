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
     * 管理費用
     */
    private int managementFee = Constant.MANAGEMENT_FEE;

    public Association(int productIndex, Member member) {
        this.productIndex = productIndex;
        this.member = member;
    }

    public int payCharge() {
        member.setFunds(member.getFunds() - charge);
        return charge;
    }


}
