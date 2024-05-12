package com.virtualwallet.demo.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Config
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }

}
