package com.example.work.controller;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.work.common.Response;
import com.example.work.entity.User;
import com.example.work.service.UserService;
import com.example.work.usermapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/")
public class PostController {
    private static Cookie cookie;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

    int[] faketree = {469, 697, 905, 817, 206, 659, 798, 942, 187, 622, 337, 799, 100, 465, 455, 956, 86, 765, 1023, 431, 19, 827, 301, 536, 70, 158, 374, 756, 976, 554, 470, 588, 270, 388, 941, 73, 165, 488, 232, 627, 518, 480, 45, 844, 389, 763, 240, 287, 108, 603, 833, 660, 583, 929, 863, 803, 142, 555, 720, 62, 176, 544, 837, 485, 418, 729, 998, 993, 51, 472, 873, 384, 239, 5, 475, 861, 843, 474, 259, 825, 918, 201, 415, 563, 944, 607, 264, 500, 227, 520, 186, 39, 574, 328, 8, 434, 791, 832, 537, 797, 527, 774, 244, 370, 40, 107, 752, 693, 909, 894, 394, 410, 263, 306, 396, 858, 1003, 18, 188, 675, 860, 135, 426, 883, 816, 255, 210, 505, 571, 277, 699, 320, 71, 911, 74, 486, 351, 535, 140, 327, 404, 824, 354, 892, 674, 153, 549, 940, 340, 288, 27, 766, 907, 722, 522, 900, 295, 189, 151, 175, 539, 377, 854, 362, 332, 886, 511, 162, 967, 110, 739, 468, 792, 23, 1008, 67, 128, 95, 303, 487, 969, 438, 995, 261, 471, 262, 99, 621, 34, 247, 92, 692, 573, 492, 375, 582, 196, 568, 672, 61, 182, 545, 954, 594, 961, 606, 805, 1010, 702, 829, 243, 59, 510, 341, 400, 965, 533, 414, 246, 11, 913, 743, 365, 193, 602, 393, 234, 543, 771, 448, 79, 416, 109, 651, 800, 831, 617, 213, 452, 112, 777, 87, 429, 121, 183, 379, 348, 856, 297, 710, 369, 367, 955, 601, 634, 595, 530, 345, 169, 662, 253, 684, 671, 828, 314, 229, 191, 453, 948, 950, 738, 3, 132, 289, 717, 317, 381, 106, 63, 447, 709, 55, 593, 339, 733, 558, 696, 872, 137, 506, 711, 203, 498, 600, 780, 359, 397, 184, 770, 517, 553, 131, 524, 888, 260, 566, 749, 548, 97, 148, 811, 938, 174, 508, 391, 221, 790, 57, 939, 705, 788, 507, 647, 299, 479, 64, 849, 737, 467, 926, 272, 585, 430, 960, 219, 570, 880, 155, 813, 930, 760, 312, 815, 433, 725, 687, 482, 747, 124, 640, 43, 177, 564, 123, 230, 127, 807, 784, 613, 915, 937, 897, 238, 119, 1004, 546, 41, 616, 719, 390, 54, 267, 789, 946, 512, 992, 273, 1015, 979, 209, 559, 504, 130, 576, 501, 624, 515, 439, 154, 891, 681, 502, 283, 688, 669, 98, 222, 964, 28, 623, 266, 313, 552, 605, 308, 778, 618, 925, 1019, 226, 550, 936, 989, 378, 959, 256, 2, 963, 994, 252, 641, 56, 477, 44, 363, 178, 366, 376, 53, 361, 17, 757, 368, 205, 237, 584, 349, 364, 962, 122, 604, 293, 407, 432, 884, 494, 781, 1020, 493, 149, 190, 516, 35, 572, 409, 65, 235, 231, 982, 286, 88, 890, 104, 526, 324, 701, 291, 307, 528, 881, 629, 208, 685, 896, 521, 882, 565, 931, 425, 741, 276, 577, 609, 315, 347, 138, 392, 645, 458, 612, 852, 980, 562, 597, 945, 152, 451, 436, 801, 424, 1022, 1006, 249, 372, 1021, 678, 168, 1017, 735, 497, 463, 665, 489, 49, 636, 265, 1002, 668, 50, 503, 42, 204, 401, 398, 216, 386, 802, 637, 714, 251, 614, 330, 323, 281, 343, 167, 838, 847, 449, 473, 779, 814, 412, 300, 842, 316, 444, 768, 919, 846, 898, 639, 984, 869, 245, 850, 285, 899, 236, 338, 423, 567, 751, 557, 68, 783, 157, 935, 865, 22, 957, 677, 150, 615, 1, 309, 269, 223, 851, 114, 101, 586, 395, 80, 912, 835, 81, 352, 280, 655, 732, 727, 282, 195, 1001, 643, 133, 745, 927, 274, 953, 764, 459, 921, 290, 906, 31, 908, 356, 541, 619, 16, 48, 445, 598, 373, 862, 26, 514, 592, 319, 910, 523, 77, 818, 331, 812, 648, 83, 867, 476, 405, 143, 20, 194, 278, 580, 333, 848, 578, 37, 302, 509, 311, 385, 810, 579, 730, 632, 417, 839, 670, 656, 170, 454, 60, 611, 866, 357, 819, 758, 625, 334, 809, 496, 821, 654, 754, 804, 199, 120, 718, 160, 304, 76, 630, 1024, 657, 551, 587, 91, 795, 271, 695, 268, 978, 734, 241, 973, 491, 853, 346, 440, 435, 532, 513, 7, 441, 761, 403, 728, 495, 25, 38, 904, 667, 1000, 968, 708, 82, 834, 296, 599, 258, 85, 310, 864, 715, 991, 985, 420, 879, 1005, 707, 242, 93, 589, 680, 1007, 181, 159, 129, 380, 30, 644, 649, 569, 248, 664, 988, 903, 855, 466, 575, 762, 192, 958, 786, 987, 785, 14, 683, 214, 700, 895, 355, 974, 298, 179, 525, 782, 419, 689, 556, 724, 4, 163, 254, 990, 225, 47, 529, 542, 29, 10, 951, 103, 443, 126, 679, 830, 145, 822, 996, 113, 228, 111, 344, 750, 917, 115, 294, 1014, 744, 437, 875, 172, 841, 336, 36, 117, 966, 211, 943, 859, 24, 360, 975, 706, 628, 682, 156, 326, 89, 94, 924, 874, 631, 977, 46, 171, 691, 776, 350, 12, 1016, 499, 118, 638, 902, 292, 652, 534, 690, 836, 200, 450, 726, 383, 704, 650, 321, 371, 934, 1013, 6, 999, 446, 769, 986, 642, 75, 146, 406, 793, 775, 318, 772, 663, 949, 257, 676, 72, 116, 212, 399, 9, 971, 180, 773, 877, 102, 723, 547, 250, 166, 673, 820, 887, 202, 413, 483, 217, 731, 279, 33, 460, 90, 519, 755, 408, 590, 561, 224, 703, 845, 387, 284, 653, 428, 713, 538, 531, 620, 207, 658, 325, 421, 742, 635, 66, 484, 753, 871, 220, 136, 748, 58, 490, 411, 78, 464, 1009, 633, 342, 134, 746, 456, 173, 868, 305, 233, 806, 808, 402, 889, 823, 164, 626, 933, 736, 335, 105, 686, 740, 358, 666, 144, 952, 885, 422, 52, 560, 716, 197, 462, 901, 96, 610, 914, 21, 878, 275, 794, 139, 646, 141, 767, 932, 125, 1018, 329, 382, 461, 840, 983, 928, 581, 661, 198, 32, 796, 1012, 694, 591, 478, 69, 870, 876, 923, 947, 893, 997, 981, 608, 759, 712, 922, 970, 698, 161, 15, 916, 427, 972, 13, 787, 721, 215, 1011, 826, 147, 442, 920, 185, 481, 84, 322, 218, 457, 540, 353, 857, 596};

    private RestTemplate restTemplate = new RestTemplate();

    // app登录
    @PostMapping(value="applogin")
    public String applogin(HttpServletResponse response,
                      @RequestParam(value="username", required = true) String username,
                      @RequestParam(value="password", required = true) String password,
                        HttpSession session){
        User user = userService.selectByUserName(username);
        Boolean isMatch = pbkdf2PasswordEncoder.matches(password, user.getPassword());
        if(isMatch){
            cookie = new Cookie("login", "success");
            session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.setAttribute("username", username);
            response.addCookie(cookie);
            return "Y";
        }else{
            return "N";
        }
    }

    // 网页登录
    @PostMapping(value = "login")
    public Response<User> login(User user, HttpSession session) {
        System.out.println(user.toString());
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return Response.error("用户名或密码不能为空");
        }
        User queryUser = new User();
        queryUser.setUsername(user.getUsername());
        if (StringUtils.matches("admin", user.getUsername()) && StringUtils.matches("admin", user.getPassword())) {
            session.setAttribute("user", queryUser);
            return Response.success("登录成功", queryUser);
        }
        return Response.error("用户名或密码错误");
    }

    // app注册
    @PostMapping(value="register")
    public String register(String username, String password){
        System.out.println(username+"-"+password);
        User user = new User();
        Random random = new Random();
        user.setUsername(username);
        user.setPhonenumber(Long.toString((long)Math.pow(10,11)+random.nextInt((int)Math.pow(10,11))));
        user.setUuid(Long.toHexString((long)Math.pow(2,63)+random.nextInt((int)Math.pow(2,63))));
        String pp = pbkdf2PasswordEncoder.encode(password);
        user.setPassword(pp);
        user.setStatus("negative");
        System.out.println(user);
        try {
            User user1 = userService.selectByUserName(username);
            System.out.println(user1.toString());
        }catch (Exception e){
            userMapper.insert(user);
            return user.getUuid();
        }
        return "No";
    }

    // 解密接口一：用于功能一和功能三
    @PostMapping(value = "api")
    public Map<String, String> api(String data){
        System.out.println("decrypting: " + data);
        ArrayList<List<String>> list = (ArrayList<List<String>>) JSON.parseObject(data).get("data");
        SHE she = new SHE();
        List<Integer> reslist = new ArrayList<>();
        for(int i=0; i< list.size();i++){
            reslist.add(she.SHECompare(new BigInteger(list.get(i).get(0)), new BigInteger(list.get(i).get(1))));
        }
        Map<String, String> res = new HashMap<>();
        if(reslist.contains(1)){
            res.put("res", "0");
        }else{
            res.put("res", "1");
        }
        return res;
    }

    // 解密接口二：用于功能二
    @PostMapping(value = "placeapi")
    public Map<String, String> placeapi(String data){
        System.out.println("decrypting: " + data);
        ArrayList<ArrayList<BigInteger>> list = (ArrayList<ArrayList<BigInteger>>) JSON.parseObject(data).get("data");
        SHE she = new SHE();
        int[] reslist = new int[list.size()];
        for(int i = 0; i<list.size(); i++){
            reslist[i] = she.SHECompare(list.get(i).get(0), list.get(i).get(1));
        }
        Arrays.sort(reslist);
        Map<String, String> res = new HashMap<>();
        if(reslist[0]>=0 || reslist[reslist.length-1]<=0){
            res.put("res", "1");
        }else{
            res.put("res", "0");
        }
        return res;
    }

    // app端用户状态获取模块
    @PostMapping(value = "status")
    public String getStatus(String data){
        String name = (String) JSON.parseObject(data).get("name");
        User user = userService.selectByUserName(name);
        System.out.println(user.toString());
        if(user.getStatus().equals("positive")){
            return "1";
        }else{
            return "0";
        }
    }

    // app端获取混淆后的四叉树节点
    @PostMapping(value = "tree")
    public String tree(String data){
        String tid = (String) JSON.parseObject(data).get("treeid");
        SHE she = new SHE();
        int treeid = she.SHEDecryption(new BigInteger(tid, 16)).intValue();
        System.out.println(treeid);
        if(treeid<1024&&treeid>=0)
            return Integer.toHexString(faketree[treeid]);
        else
            return "hack";
    }

    // 功能一
    @PostMapping(value = "idsearch")
    public Response<String> searchId(String usernames){
        List<String> arrayList = new ArrayList<>();
        for(String u: usernames.split(";")){
            arrayList.add(userService.selectByUserName(u).getUuid());
        }
        String uuids = String.join(";", arrayList);

        long stime = System.currentTimeMillis();

        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuids);
        LinkedMultiValueMap<String, String> newmap = new LinkedMultiValueMap<>();
        newmap.add("data",  JSONObject.toJSONString(map));

        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(newmap, httpHeaders);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(
                "http://127.0.0.1:8081/idsearch",httpEntity,String.class
        );
        String result = (String) apiResponse.getBody();
        System.out.println("time: "+(System.currentTimeMillis()-stime));

        return Response.success("yes", result);
    }

    // 功能二
    @PostMapping(value = "placesearch")
    public Response<String> searchPlace(String stime, String etime, String areas){
        String[] points = areas.split(";");

        long sttime = System.currentTimeMillis();
        List<String[]> area0 = new ArrayList<>();
        List<BigInteger[]> area = new ArrayList<>();
        for(int i=0; i<points.length; i++){
            area0.add(points[i].split(","));
        }
        for(int i=0; i< area0.size(); i++){
            area.add(new BigInteger[] {new BigInteger(area0.get(i)[0]), new BigInteger(area0.get(i)[1])});
        }
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
        PointInRectangle pointInRectangle = new PointInRectangle();
        QuadTree quadTree = new QuadTree();
        for(BigInteger x: treex){
            for(BigInteger y: treey){
                if(pointInRectangle.plainIsIn(area, new BigInteger[] {x,y})){
                    int r = quadTree.treeIds(x1, y1, x2, y2, x, y);
                    treeIdList.add(String.valueOf(faketree[r]));
                }
            }
        }
        for(BigInteger[] a: area){
            int r = quadTree.treeIds(x1, y1, x2, y2, a[0], a[1]);
            treeIdList.add(String.valueOf(faketree[r]));
        }
        System.out.println(String.join(",", treeIdList));
        List<List<BigInteger>> newarea = new ArrayList<>();
        SHE she = new SHE();
        for(int i=0; i<area.size(); i++){
            List<BigInteger> tarea = new ArrayList<>();
            tarea.add(she.SHEEncryption(area.get(i)[0].negate()));
            tarea.add(she.SHEEncryption(area.get(i)[1].negate()));
            newarea.add(tarea);
        }
        Map<String, Object> map = new HashMap<>();
        List<String> time = new ArrayList<>();
        time.add(stime);
        time.add(etime);
        map.put("time", time);
        map.put("treeid", treeIdList);
        map.put("area", newarea);

        LinkedMultiValueMap<String, String> newmap = new LinkedMultiValueMap<>();
        newmap.add("data",  JSONObject.toJSONString(map));

        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(newmap, httpHeaders);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(
                "http://127.0.0.1:8081/placesearch",httpEntity,String.class
        );
        String result = apiResponse.getBody();
        System.out.println("time: "+(System.currentTimeMillis()-sttime));

        return Response.success("yes", result);
    }

    // 功能三
    @PostMapping(value = "relasearch")
    public Response<Object> relasearch(String usernames){
        String[] names = usernames.split(";");
        List<String> arrayList = new ArrayList<>();
        for(String u: usernames.split(";")){
            arrayList.add(userService.selectByUserName(u).getUuid());
        }
        String uuids = String.join(";", arrayList);

        Long stime = System.currentTimeMillis();
        System.out.println(uuids);
        Map<String, String> map = new HashMap<>();
        map.put("uuids", uuids);
        LinkedMultiValueMap<String, String> newmap = new LinkedMultiValueMap<>();
        newmap.add("data",  JSONObject.toJSONString(map));
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
        httpHeaders.setContentType(type);
        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(newmap, httpHeaders);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(
                "http://127.0.0.1:8081/relasearch",httpEntity,String.class
        );
        String result = apiResponse.getBody();
        List<Map<String, Object>> edge = new ArrayList<>();
        for(String s: result.split(";")){
            Map<String, Object> maps = new HashMap<>();
            maps.put("source", names[arrayList.indexOf(s.split("-")[0])]);
            maps.put("target", names[arrayList.indexOf(s.split("-")[1])]);
            maps.put("weight", 1);
            edge.add(maps);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("node", names);
        res.put("edge", edge);
        System.out.println("time: "+(System.currentTimeMillis()-stime));
        return Response.success("yes", res);
    }

}
