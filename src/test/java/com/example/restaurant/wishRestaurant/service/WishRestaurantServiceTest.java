package com.example.restaurant.wishRestaurant.service;


import com.example.restaurant.wishrestaurant.dto.WishRestaurantDto;
import com.example.restaurant.wishrestaurant.service.WishRestaurantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishRestaurantServiceTest {

    @Autowired
    private WishRestaurantService wishRestaurantService;

    @Test
    public void searchTest(){
        var result = wishRestaurantService.search("갈비집");

        System.out.println(result);

        Assertions.assertNotNull(result);
    }

}
