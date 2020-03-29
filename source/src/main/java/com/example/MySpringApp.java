package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


@SpringBootApplication
public class MySpringApp {

    public static void main(String[] args) throws IOException {
        String ip;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        }
        ConfigurableApplicationContext context = SpringApplication.run(MySpringApp.class,args);
        ConfigurableEnvironment configurableEnvironment = context.getEnvironment();
        String port = configurableEnvironment.getProperty("local.server.port");
        System.out.println("\n** СЕРВЕР ЗАПУЩЕН. ИСПОЛЬЗУЙТЕ "+ip+":"+port+" ДЛЯ ПОДКЛЮЧЕНИЯ **\n");
    }
}