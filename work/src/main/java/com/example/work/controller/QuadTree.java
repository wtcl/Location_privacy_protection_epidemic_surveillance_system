package com.example.work.controller;

import java.math.BigInteger;
import java.util.ArrayList;

public class QuadTree {
    public static void traceTree(BigInteger x1, BigInteger y1, BigInteger x2, BigInteger y2, BigInteger px, BigInteger py, int height, ArrayList<Integer> tree){
        BigInteger centerx = x1.divide(new BigInteger("2")).add(x2.divide(new BigInteger("2")));
        BigInteger centery = y1.divide(new BigInteger("2")).add(y2.divide(new BigInteger("2")));
//        System.out.println(centerx.toString()+"-"+centery.toString());
        if(judge(x1, y1, centerx, centery, px, py)){
            tree.add(1);
            if(tree.size()<height){
                traceTree(x1, y1, centerx, centery, px, py, height, tree);
            }
        }else if(judge(centerx, centery, x2, y2, px, py)){
            tree.add(4);
            if(tree.size()<height){
                traceTree(centerx, centery, x2, y2, px, py, height, tree);
            }
        }else if(judge(x1, centery, centerx, y2, px, py)){
            tree.add(3);
            if(tree.size()<height){
                traceTree(x1, centery, centerx, y2, px, py, height, tree);
            }
        }else if(judge(centerx, y1, x2, centery, px, py)) {
            tree.add(2);
            if (tree.size() < height) {
                traceTree(centerx, y1, x2, centery, px, py, height, tree);
            }
        }
    }

    public static boolean judge(BigInteger xl, BigInteger yl, BigInteger xr, BigInteger yr, BigInteger x, BigInteger y){
        if((xl.subtract(x).multiply(xr.subtract(x))).compareTo(BigInteger.ZERO)<=0 && (yl.subtract(y).multiply(yr.subtract(y))).compareTo(BigInteger.ZERO)<=0){
            return true;
        }else {
            return false;
        }
    }

    public static int treeId(BigInteger x1, BigInteger y1, BigInteger x2, BigInteger y2, BigInteger px, BigInteger py){
        ArrayList<Integer> list = new ArrayList<>();
        traceTree(x1, y1, x2, y2, px, py, 5, list);
        int id = 0;
        for(int i:list){
            id = id*4 + i-1;
//            System.out.println(i);
        }
        return id;
    }
    public int treeIds(BigInteger x1, BigInteger y1, BigInteger x2, BigInteger y2, BigInteger px, BigInteger py) {
        return treeId(x1, y1, x2, y2, px, py);
    }

    public static void main(String[] args){
        int ids;
//        ids = treeId(new BigInteger("0"), new BigInteger("1000"), new BigInteger("1000"), new BigInteger("0"), new BigInteger("510"), new BigInteger("510"));
        BigInteger x1 = new BigInteger("11966845");
        BigInteger y1 = new BigInteger("4096139");
        BigInteger x2 = new BigInteger("12078164");
        BigInteger y2 = new BigInteger("4028802");
        System.out.println(treeId(x1,y1,x2,y2,new BigInteger("12070000"), new BigInteger("4095105")));
//        System.out.println("ids"+ids);
    }

}
