package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> findAll() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo findById(@PathVariable Integer id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getId() == null || kangaroo.getName() == null) {
            throw new ZooException("Invalid kangaroo data", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable Integer id, @RequestBody Kangaroo kangaroo) {
        kangaroos.put(id, new Kangaroo(id, kangaroo.getName(), kangaroo.getHeight(), kangaroo.getWeight(), kangaroo.getGender(), kangaroo.getIsAggressive()));
        return kangaroos.get(id);
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable Integer id){
        Kangaroo kangaroo = kangaroos.get(id);
        kangaroos.remove(id);
        return kangaroo;
    }
}