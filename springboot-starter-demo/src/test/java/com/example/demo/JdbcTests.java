package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class JdbcTests {

    @Autowired
    JdbcTemplate db2JdbcTemplate;

    @Test
    public void addDataData() {

        String sql = "insert into user_info(name,age) values('mic1',18)";
        db2JdbcTemplate.execute(sql);
    }
}
