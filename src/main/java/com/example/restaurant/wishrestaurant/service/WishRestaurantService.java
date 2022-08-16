package com.example.restaurant.wishrestaurant.service;

import com.example.restaurant.naver.NaverClient;
import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.wishrestaurant.dto.WishRestaurantDto;
import com.example.restaurant.wishrestaurant.entity.WishRestaurantEntity;
import com.example.restaurant.wishrestaurant.repository.WishRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishRestaurantService {

    private final NaverClient naverClient;
    private final WishRestaurantRepository wishRestaurantRepository;

    public WishRestaurantDto search(String query) {

        //지역검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);
        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        // 검색된 데이터가 있을 때
        if(searchLocalRes.getTotal() > 0 ){
            //첫 번째 아이템 꺼내기
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

            //title에 잡다한 문자를 없앤다. (검색을 용이하게 만듦)
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            //이미지 검색
            var searchImageRes = naverClient.searchImage(searchImageReq);

            if(searchImageRes.getTotal() > 0) {

                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                //결과를 리턴
                var result = new WishRestaurantDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(localItem.getLink());

                return result;
            }


        }
        return new WishRestaurantDto();

    }


    public WishRestaurantDto add(WishRestaurantDto wishRestaurantDto) {
        var entity = dtoToEntity(wishRestaurantDto);
        var saveEntity = wishRestaurantRepository.save(entity);
        return entityToDto(saveEntity);

    }

    private WishRestaurantEntity dtoToEntity(WishRestaurantDto wishRestaurantDto){
        var entity = new WishRestaurantEntity();
        entity.setTitle(wishRestaurantDto.getTitle());
        entity.setIndex(wishRestaurantDto.getIndex());
        entity.setCategory(wishRestaurantDto.getCategory());
        entity.setAddress(wishRestaurantDto.getAddress());
        entity.setRoadAddress(wishRestaurantDto.getRoadAddress());
        entity.setHomePageLink(wishRestaurantDto.getHomePageLink());
        entity.setImageLink(wishRestaurantDto.getImageLink());
        entity.setVisit(wishRestaurantDto.isVisit());
        entity.setVisitCount(wishRestaurantDto.getVisitCount());
        entity.setLastVisitData(wishRestaurantDto.getLastVisitData());

        return entity;
    }

    private WishRestaurantDto entityToDto(WishRestaurantEntity wishRestaurantEntity){
        var dto = new WishRestaurantDto();
        dto.setTitle(wishRestaurantEntity.getTitle());
        dto.setIndex(wishRestaurantEntity.getIndex());
        dto.setCategory(wishRestaurantEntity.getCategory());
        dto.setAddress(wishRestaurantEntity.getAddress());
        dto.setRoadAddress(wishRestaurantEntity.getRoadAddress());
        dto.setHomePageLink(wishRestaurantEntity.getHomePageLink());
        dto.setImageLink(wishRestaurantEntity.getImageLink());
        dto.setVisit(wishRestaurantEntity.isVisit());
        dto.setVisitCount(wishRestaurantEntity.getVisitCount());
        dto.setLastVisitData(wishRestaurantEntity.getLastVisitData());

        return dto;
    }

    public List<WishRestaurantDto> findAll() {

        return wishRestaurantRepository.findAll()
                .stream()
                .map(it -> entityToDto(it))
                .collect(Collectors.toList());

    }

    public void delete(int index) {
        wishRestaurantRepository.deleteById(index);
    }

    public void addVisit(int index){
        var wishItem = wishRestaurantRepository.findById(index);
        if(wishItem.isPresent()){
            var item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount()+1);
        }
    }
}
