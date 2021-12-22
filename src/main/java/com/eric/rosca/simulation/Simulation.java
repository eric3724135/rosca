package com.eric.rosca.simulation;

import com.eric.rosca.domain.Member;
import com.eric.rosca.domain.ProductSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Simulation {

    private int memberNum = 25;
    private List<ProductSet> productSets;

    public void start() {
        //每期結算次數
        int count = 0;
        while (count > 100) {
            if (count == 0) {

            }


            count++;
        }
        try {
            init();
            log.info("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
