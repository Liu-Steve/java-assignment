package edu.whu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"edu.whu.service.impl", "edu.whu.dao.impl"})
public class BookConfig {
}
