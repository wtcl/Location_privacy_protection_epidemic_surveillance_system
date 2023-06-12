package com.example.work;

import com.example.work.controller.QuadTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WorkApplication.class)
public class QuadTreeTests {

    public QuadTree quadTree = new QuadTree();
    @Test
    public void quadtree(){
        BigInteger x1 = new BigInteger("11966845");
        BigInteger y1 = new BigInteger("4096139");
        BigInteger x2 = new BigInteger("12078164");
        BigInteger y2 = new BigInteger("4028802");
        System.out.println(quadTree.treeIds(x1,y1,x2,y2,new BigInteger("12070000"), new BigInteger("4060000")));
        System.out.println(quadTree.treeIds(x1,y1,x2,y2,new BigInteger("12000000"), new BigInteger("4060000")));
        System.out.println(quadTree.treeIds(x1,y1,x2,y2,new BigInteger("12000000"), new BigInteger("4035105")));
        System.out.println(quadTree.treeIds(x1,y1,x2,y2,new BigInteger("12059030"), new BigInteger("4046688")));
    }
}
