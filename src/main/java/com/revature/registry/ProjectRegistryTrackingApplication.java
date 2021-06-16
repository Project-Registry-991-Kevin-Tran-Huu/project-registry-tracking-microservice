package com.revature.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProjectRegistryTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectRegistryTrackingApplication.class, args);
    }

}
