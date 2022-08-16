package com.example.restaurant.controller;

import com.example.restaurant.wishrestaurant.dto.WishRestaurantDto;
import com.example.restaurant.wishrestaurant.service.WishRestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class ApiController {

    private final WishRestaurantService wishRestaurantService;

    @GetMapping("/search")
    public WishRestaurantDto search(@RequestParam String query){
        return wishRestaurantService.search(query);
    }

    @PostMapping("")
    public WishRestaurantDto add(@RequestBody WishRestaurantDto wishRestaurantDto){
        log.info("{}", wishRestaurantDto);

        return wishRestaurantService.add(wishRestaurantDto);
    }

    @GetMapping("/all")
    public List<WishRestaurantDto> findAll(){
        return wishRestaurantService.findAll();
    }

    @DeleteMapping("/{index}")
    public void delete(@PathVariable int index){
        wishRestaurantService.delete(index);

    }

    @PostMapping("/{index}")
    public void addVisit(@PathVariable int index){
        wishRestaurantService.addVisit(index);
    }

}
