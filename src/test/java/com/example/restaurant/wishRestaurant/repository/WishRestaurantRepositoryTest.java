package com.example.restaurant.wishRestaurant.repository;

import com.example.restaurant.wishrestaurant.entity.WishRestaurantEntity;
import com.example.restaurant.wishrestaurant.repository.WishRestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishRestaurantRepositoryTest {

    @Autowired
    private WishRestaurantRepository wishRestaurantRepository;

    private WishRestaurantEntity create(){

        var wishRestaurant = new WishRestaurantEntity();
        wishRestaurant.setTitle("title");
        wishRestaurant.setCategory("category");
        wishRestaurant.setAddress("address");
        wishRestaurant.setRoadAddress("readAddress");
        wishRestaurant.setHomePageLink("");
        wishRestaurant.setImageLink("");
        wishRestaurant.setVisit(false);
        wishRestaurant.setVisitCount(0);
        wishRestaurant.setLastVisitData(null);
        return wishRestaurant;
    }

    @Test
    public void saveTest(){

        var wishRestaurantEntity = create();
        var expected = wishRestaurantRepository.save(wishRestaurantEntity);

        Assertions.assertNotNull(expected);
        Assertions.assertEquals(1, expected.getIndex());
    }

    @Test
    public void updateTest(){

        var wishRestaurantEntity = create();
        var expected = wishRestaurantRepository.save(wishRestaurantEntity);

        expected.setTitle("update test");
        var saveEntity = wishRestaurantRepository.save(expected);

        Assertions.assertEquals("update test", saveEntity.getTitle());
        Assertions.assertEquals(1, wishRestaurantRepository.findAll().size());
    }

    @Test
    public void findByIdTest(){
        var wishRestaurantEntity = create();
        wishRestaurantRepository.save(wishRestaurantEntity);

        var expected = wishRestaurantRepository.findById(1);

        Assertions.assertEquals(true, expected.isPresent());
        Assertions.assertEquals(1, expected.get().getIndex());

    }

    @Test
    public void deleteTest(){
        var wishRestaurantEntity = create();
        wishRestaurantRepository.save(wishRestaurantEntity);

        wishRestaurantRepository.deleteById(1);

        int count = wishRestaurantRepository.findAll().size();

        Assertions.assertEquals(0, count);
    }

    @Test
    public void listAllTest(){

        var wishRestaurantEntity1 = create();
        wishRestaurantRepository.save(wishRestaurantEntity1);

        var wishRestaurantEntity2 = create();
        wishRestaurantRepository.save(wishRestaurantEntity2);

        int count = wishRestaurantRepository.findAll().size();

        Assertions.assertEquals(2, count);

    }

}
