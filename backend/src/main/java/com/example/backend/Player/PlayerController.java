package com.example.backend.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    PlayerRepository repository;
    @GetMapping("/all")
    public List<Player> listPlayers(){
        return repository.findAll();
    }

    @PostMapping("/create")
    public Player createPlayer(){
        Player p = new Player("Francisco");
        return repository.save(p);
    }
}
