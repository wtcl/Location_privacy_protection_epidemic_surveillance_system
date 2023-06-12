package com.example.cloud.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.cloud.dao.TracksMapper;
import com.example.cloud.entity.TracksInfo;
import com.example.cloud.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/")
public class PostController {

    @Autowired
    private TracksMapper tracksMapper;
    @Autowired
    private SyncService syncService;

    @PostMapping(value = "/upload")
    public String upload(String data) {
        System.out.println("接收到来自"+data.split(",")[1]+"的数据");
        Double nowtime = Double.valueOf(System.currentTimeMillis());
        System.out.println("时间相差"+(nowtime-Integer.valueOf(data.split(",")[0]))+"秒");
        System.out.println("时间戳校验成功，数据未被拦截修改");
        System.out.println("用户数据如下"+"\n"+data.replace(",","\n"));
        return "1";
    }


    @PostMapping(value = "/idsearch")
    public String idsearch(String data) throws InterruptedException, ExecutionException {
        String uuid = (String) JSON.parseObject(data).get("uuid");

        System.out.println("接收到基于人类阳性的密接筛查功能请求，开始查询");

        long time1 = System.currentTimeMillis();

        String[] uuids = uuid.split(";");
        TracksInfo[] illTrack = tracksMapper.selectOneMain(uuids);
        ArrayList<String> foundId = new ArrayList<>();

        List<Future<String>> futureList = new ArrayList<>();

        for(TracksInfo p: illTrack){
            BigInteger t = p.getT();
            BigInteger px = new BigInteger(p.getX(), 16);
            BigInteger py = new BigInteger(p.getY(), 16);
            System.out.println("sick-location x: "+px+" y: "+py);
            String[] tancids = p.getTancid().split(";");
            TracksInfo[] healthTrack = tracksMapper.selectOneSub(t,uuids,foundId.toArray(new String[foundId.size()]), tancids);
            for(TracksInfo q: healthTrack){
                BigInteger qx = new BigInteger(q.getX(), 16);
                BigInteger qy = new BigInteger(q.getY(), 16);
                System.out.println("healthy-location x: "+qx+" y: "+qy);
                ArrayList<List<String>> dis = new ArrayList<>();

                List<String> d0 = new ArrayList<>();
                d0.add(qx.multiply(qx).add(px.multiply(px)).toString());
                d0.add(qx.multiply(px).add(qx.multiply(px)).add(new BigInteger("30000")).toString());

                List<String> d1 = new ArrayList<>();
                d1.add(qy.multiply(qy).add(py.multiply(py)).toString());
                d1.add(qy.multiply(py).add(qy.multiply(py)).add(new BigInteger("30000")).toString());

                dis.add(d0);
                dis.add(d1);

                Map<String, ArrayList<List<String>>> rawMap = new HashMap<>();
                rawMap.put("data", dis);

                futureList.add(syncService.method1(rawMap, q.getUuid()));

//                WebClient client = WebClient.create();
//                @SuppressWarnings("rawtypes")
//                Flux<Map> mapFlux = client.post().uri("http://127.0.0.1:8080/api").contentType(MediaType.APPLICATION_FORM_URLENCODED).body(BodyInserters.fromFormData("data",JSONObject.toJSONString(rawMap))).retrieve().bodyToFlux(Map.class);
//                mapFlux.collectList().subscribe(maps -> {
//                    String result = (String) maps.get(0).get("res");
//                    System.out.println("result"+result);
//                    if(result.equals("1")) foundId.add(q.getUuid());
//                });
            }
        }
//        Thread.sleep(10);
        for(Future<String> res: futureList){
            if(!res.get().equals("")){
                foundId.add(res.get());
            }
        }
        System.out.println("time: "+(System.currentTimeMillis()-time1)+"ms");
        System.out.println("查询结果：" + String.join(";", foundId));
        return String.join(";", foundId);
    }

    @PostMapping(value = "/placesearch")
    public String placesearch(String data) throws InterruptedException, ExecutionException {
        System.out.println("接收到基于环境阳性的密接筛查功能请求，开始查询");

        BigInteger st = new BigInteger(((ArrayList<String>) JSON.parseObject(data).get("time")).get(0));
        BigInteger et = new BigInteger(((ArrayList<String>) JSON.parseObject(data).get("time")).get(1));
        ArrayList<String> treeId = (ArrayList<String>) JSON.parseObject(data).get("treeid");
        ArrayList<ArrayList<BigInteger>> area = (ArrayList<ArrayList<BigInteger>>) JSON.parseObject(data).get("area");
        ArrayList<String> foundId = new ArrayList<>();
        long stime = System.currentTimeMillis();


        List<Future<String>> futureList = new ArrayList<>();

        while(st.compareTo(et)<=0){
            TracksInfo[] tracksInfos = tracksMapper.selectTwoMain(st,foundId.toArray(new String[foundId.size()]),treeId.toArray(new String[treeId.size()]));
            for(TracksInfo p: tracksInfos){
                BigInteger x = new BigInteger(p.getX(), 16);
                BigInteger y = new BigInteger(p.getY(), 16);
                System.out.println("point t: "+ st +" x: "+x+" y: "+y);
                ArrayList<ArrayList<BigInteger>> dis= new ArrayList<>();
                for(int i=0; i<area.size()-1; i++){
                    ArrayList<BigInteger> d = new ArrayList<>();
                    d.add((x.add(area.get(i).get(0))).multiply(y.add(area.get(i+1).get(1))));
                    d.add(x.add(area.get(i+1).get(0)).multiply(y.add(area.get(i).get(1))));
                    dis.add(d);
                }
                Map<String, ArrayList<ArrayList<BigInteger>>> rawMap = new HashMap<>();
                rawMap.put("data", dis);

                futureList.add(syncService.method2(rawMap, p.getUuid()));

//                WebClient client = WebClient.create();
//                @SuppressWarnings("rawtypes")
//                Flux<Map> mapFlux = client.post().uri("http://127.0.0.1:8080/placeapi").contentType(MediaType.APPLICATION_FORM_URLENCODED).body(BodyInserters.fromFormData("data",JSONObject.toJSONString(rawMap))).retrieve().bodyToFlux(Map.class);
//                mapFlux.collectList().subscribe(maps -> {
//                    String result = (String) maps.get(0).get("res");
//                    if(result.equals("1")) foundId.add(p.getUuid());
//                    System.out.println("result"+result);
//                });
            }
            st = st.add(new BigInteger("900"));
        }

        for(Future<String> res: futureList){
            if(!res.get().equals("")){
                foundId.add(res.get());
            }
        }

        System.out.println("time: "+ (System.currentTimeMillis()-stime)+"ms");
        System.out.println("查询结果：" + String.join(";", foundId));
        return String.join(";", foundId);
    }

    @PostMapping(value = "/relasearch")
    public String relasearch(String data) throws UnsupportedEncodingException, InterruptedException {
        System.out.println("接收到现有感染者关联度查询功能请求，开始查询");

        String[] uuids = ((String) JSON.parseObject(data).get("uuids")).split(";");
        List<String> relationship = new ArrayList<>();
        long stime = System.currentTimeMillis();
//        循环任务
        for(int i=0; i<uuids.length-1; i++){
            TracksInfo[] adata = tracksMapper.selectThreeMain(uuids[i]);
            for(TracksInfo p: adata){
                TracksInfo[] bdata = tracksMapper.selectThreeSub(Arrays.copyOfRange(uuids, i+1, uuids.length), p.getT(), p.getTancid().split(";"));
                BigInteger px = new BigInteger(p.getX(), 16);
                BigInteger py = new BigInteger(p.getY(), 16);
                System.out.println("target uuid: "+ p.getUuid() + " x: " + px + " y: "+ py);
                for(TracksInfo q: bdata){
                    BigInteger qx = new BigInteger(q.getX(), 16);
                    BigInteger qy = new BigInteger(q.getY(), 16);
                    System.out.println("destination uuid: "+ q.getUuid() + " x: " + qx + " y: "+ qy);
                    ArrayList<List<String>> dis = new ArrayList<>();
                    List<String> d = new ArrayList<>();
                    BigInteger two = new BigInteger("2");
                    BigInteger three = new BigInteger("30000");
                    d.add((px.multiply(px)).add(py.multiply(py)).add(qx.multiply(qx)).add(qy.multiply(qy)).toString());
                    d.add((px.multiply(qx).multiply(two)).add(py.multiply(qy).multiply(two)).add(three).toString());
                    dis.add(d);

                    Map<String, ArrayList<List<String>>> rawMap = new HashMap<>();
                    rawMap.put("data", dis);
                    LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                    map.add("data",  JSONObject.toJSONString(rawMap));

//                    webclient异步请求
                    WebClient client = WebClient.create();
                    @SuppressWarnings("rawtypes")
                    Flux<Map> mapFlux = client.post().uri("http://127.0.0.1:8080/api").contentType(MediaType.APPLICATION_FORM_URLENCODED).body(BodyInserters.fromFormData("data",JSONObject.toJSONString(rawMap))).retrieve().bodyToFlux(Map.class);
                    mapFlux.collectList().subscribe(maps -> {
                        String result = (String) maps.get(0).get("res");
                        String res = p.getUuid()+"-"+q.getUuid();
                        if(result.equals("1") && !relationship.contains(res)) relationship.add(res);
//                        System.out.println("result"+result);
                    });
                }
            }
        }
        Thread.sleep(10);
        System.out.println("time: "+ (System.currentTimeMillis()-stime)+"ms");
        System.out.println("查询结果：" + String.join(";", relationship));
        return String.join(";", relationship);
    }
}
