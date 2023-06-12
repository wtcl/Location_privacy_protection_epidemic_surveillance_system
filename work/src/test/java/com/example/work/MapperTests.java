package com.example.work;

import com.example.work.entity.User;
import com.example.work.service.UserService;
import com.example.work.usermapper.UserMapper;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WorkApplication.class)
public class MapperTests {
    @Autowired
    private UserService userService;
    private UserMapper userMapper;
    Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

    @Test
    public void testSelectUser() throws IOException {
//        File file = new File("D://desktop/1.txt");
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        String tempString = null;
//        Faker faker = new Faker();
//        Faker faker1 = new Faker(Locale.CHINA);
//        Collection<User> collection = new ArrayList<>();
//        int i = 0;
//        while((tempString = reader.readLine())!=null){
//            String name = faker.name().lastName()+i;
//            String password = pbkdf2PasswordEncoder.encode(name);
//            User user = new User();
//            user.setUsername(name);
//            user.setPassword(password);
//            user.setUuid(tempString);
//            user.setStatus("negative");
//            user.setPhonenumber(faker1.phoneNumber().cellPhone());
//            collection.add(user);
//            i++;
//            System.out.println(i);
//        }
//
//        userService.saveBatch(collection);
        User user = userService.selectByUserName("zydzyd");
        System.out.println(user.toString());
    }
}
