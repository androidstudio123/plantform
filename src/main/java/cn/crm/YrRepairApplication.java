package cn.crm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 程序启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.crm.mapper"})
public class YrRepairApplication {
    public static void main(String[] args) {
        SpringApplication.run(YrRepairApplication.class, args);
    }







}
    