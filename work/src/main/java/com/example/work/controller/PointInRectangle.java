package com.example.work.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointInRectangle {
    public boolean plainIsIn(List<BigInteger[]> area, BigInteger[] point){
        BigInteger[] verifylist = new BigInteger[area.size()-1];
        for(int i = 0; i < area.size()-1; i++){
            BigInteger[] p0, p1;
            p0 = area.get(i);
            p1 = area.get(i+1);

            verifylist[i] = p0[0].multiply(p1[1])
                    .add(point[1].multiply(p1[0]))
                    .add(p0[1].multiply(point[0])).subtract(p0[1].multiply(p1[0])).subtract(point[0].multiply(p1[1])).subtract(p0[0].multiply(point[1]));
        }
        Arrays.sort(verifylist);
        if(verifylist[verifylist.length-1].compareTo(BigInteger.ZERO)<=0 || verifylist[0].compareTo(BigInteger.ZERO) >= 0){
            return true;
        }else{
            return false;
        }
    }
/*
密文下的数据比对需要发送给服务端进行修改
    public boolean cipherIsIn(List<BigInteger[]> area, BigInteger[] point){
        BigInteger[] verifylist = new BigInteger[area.size()-1];
        for(int i = 0; i < area.size()-1; i++){
            BigInteger[] p0, p1;
            p0 = area.get(i);
            p1 = area.get(i+1);
            verifylist[i] = p0[0].multiply(p1[1])
                    .add(point[0].multiply(p1[1]))
                    .add(p0[0].multiply(point[1])).subtract(p0[1].multiply(p1[0])).subtract(point[0].multiply(p0[1])).subtract(p1[0].multiply(point[1]));
        }
        Arrays.sort(verifylist);
        if(verifylist[verifylist.length-1].compareTo(BigInteger.ZERO)<=0 || verifylist[0].compareTo(BigInteger.ONE) >= 0){
            return true;
        }else{
            return false;
        }
    }
*/
    public static void main(String[] args){
        PointInRectangle pointInRectangle = new PointInRectangle();
        List<BigInteger[]> area = new ArrayList<>();
        area.add(new BigInteger[] {new BigInteger("12000000"), new BigInteger("4030000")});
        area.add(new BigInteger[] {new BigInteger("12060000"), new BigInteger("4030000")});
        area.add(new BigInteger[] {new BigInteger("12060000"), new BigInteger("4060000")});
        area.add(new BigInteger[] {new BigInteger("12000000"), new BigInteger("4030000")});
        area.add(new BigInteger[] {new BigInteger("12000000"), new BigInteger("4030000")});

        BigInteger x1 = new BigInteger("11966845");
        BigInteger y1 = new BigInteger("4096139");
        BigInteger x2 = new BigInteger("12078164");
        BigInteger y2 = new BigInteger("4028802");
        List<String> treeIdList = new ArrayList<>();
        BigInteger[] treex = new BigInteger[32];
        BigInteger[] treey = new BigInteger[32];
        BigInteger l = new BigInteger("111319");
        BigInteger w = new BigInteger("67337");
        for(int i=0; i<32; i++){
            treex[i] = x1.add(l.divide(new BigInteger("64")).add((l.multiply(new BigInteger(String.valueOf(i)))).divide(new BigInteger("32"))));
            treey[i] = y2.add(w.divide(new BigInteger("64")).add((w.multiply(new BigInteger(String.valueOf(i)))).divide(new BigInteger("32"))));
        }
        QuadTree quadTree = new QuadTree();
        ArrayList<String> res = new ArrayList<>();
        for(BigInteger i: treex){
            for(BigInteger j: treey){
                BigInteger[] point = new BigInteger[2];
                point[0] = i;
                point[1] = j;
                if(pointInRectangle.plainIsIn(area, point))
                    res.add(String.valueOf(quadTree.treeIds(x1,y1,x2,y2,i,j)));
            }
        }
        System.out.println(String.join(";", res));
    }
}
