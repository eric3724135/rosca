package com.eric.rosca.common;

public enum WithdrawalMode {

    //中間全額領取 雖然會有先許虧錢 但資金效率應用較好
    FULL_Withdrawal_IN_MIDDLE("中期全額領取", 13),

    //晚期全額領取 基本上不會虧錢 但要多付錢等到晚期
    FULL_Withdrawal_IN_THE_END("晚期全額領取", 20);

    private String desc;

    private int preferRound;


    WithdrawalMode(String desc, int preferRound) {
        this.desc = desc;
        this.preferRound = preferRound;
    }

    public int getPreferRound() {
        return preferRound;
    }
}
