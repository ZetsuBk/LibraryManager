package com.nttdata.librarymanager.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Message {
    private String message;
    public Message setMessage(String message){

        this.message = message;
        return this;
    }
}
