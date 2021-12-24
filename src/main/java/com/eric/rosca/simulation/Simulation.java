package com.eric.rosca.simulation;

import com.eric.rosca.common.Constant;
import com.eric.rosca.domain.Association;
import com.eric.rosca.domain.Member;
import com.eric.rosca.domain.Product;
import com.eric.rosca.domain.ProductSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class Simulation {

    private int memberNum = 25;
    private List<ProductSet> productSets = new ArrayList<>();
    private List<Product> closedProducts = new ArrayList<>();

    public void start() throws InterruptedException {
        //每期結算次數
        int count = 1;
        while (count <= 50) {
            if (count == 1) {
                try {
                    this.init();
                } catch (InterruptedException e) {
                    throw e;
                }
            }

            if (productSets == null || productSets.size() == 0) {
                throw new IllegalArgumentException("產品為空");
            }
            for (ProductSet productSet : productSets) {
                for (Iterator<Product> iter = productSet.getProducts().iterator(); iter.hasNext(); ) {
                    Product product = iter.next();
                    //每期收費
                    product.payForEachRound();
                    if (product.getRound() < Constant.ASSOCIATIONS_IN_PRODUCT) {
                        //TODO 抽出產品中獎者
                        Association winnerAssociation = product.getThisRoundWinner();
                        //TODO 選擇領取方式
                        winnerAssociation.processWithdrawal(productSet);

                    }
                    //產品結束此回合
                    product.endThisRound();
                    //TODO 檢查產品是否結束 並結算
                    if (product.getRound() <= 0) {
                        log.info("Product {} in the end ", product.getId());
                        //TODO 最後一位結算

                        iter.remove();
                        closedProducts.add(product);
                    }
                }
            }
            if (count > 1) {
                //TODO 檢查是否有足夠會員開新組合
                //TODO 開新組合
            }
            count++;
        }

        log.info("Simulation End");
    }

    private void init() throws InterruptedException {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < memberNum; i++) {
            Member member = new Member();
            member.setId(i);
            member.setName("Member" + i);
            members.add(member);
        }
        productSets = ProductSet.initProductSet(members);

    }
}
