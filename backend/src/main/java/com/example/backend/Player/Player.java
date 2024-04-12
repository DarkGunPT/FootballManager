package com.example.backend.Player;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "players")
public class Player {
    @Id
    public String id;
    public String name;

    public Player(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
