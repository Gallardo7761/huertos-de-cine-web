package net.miarma.backend.huertosdecine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = {
        "net.miarma.backend.huertosdecine",
        "net.miarma.backlib"
})
public class HuertosDeCineApplication {
    public static void main(String[] args) {
        SpringApplication.run(HuertosDeCineApplication.class, args);
    }
}

