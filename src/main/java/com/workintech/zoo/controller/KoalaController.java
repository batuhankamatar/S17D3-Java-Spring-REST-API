package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> findAll() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala findById(@PathVariable Integer id) {
        return koalas.get(id);
    }

    @PostMapping
    public Koala save(@RequestBody Koala koala) {
        koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable Integer id, @RequestBody Koala koala) {
        koalas.put(id, new Koala(id, koala.getName(), koala.getWeight(), koala.getSleepHour(), koala.getGender()));
        return koalas.get(id);
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable Integer id){
        Koala koala = koalas.get(id);
        koalas.remove(id);
        return koala;
    }
}