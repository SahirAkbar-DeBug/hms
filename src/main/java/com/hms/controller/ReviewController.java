package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @PostMapping
    public ResponseEntity<Review> write(@RequestBody Review review, @RequestParam long propertyId,
                                        @AuthenticationPrincipal AppUser user
                                        ){
        System.out.println(propertyId);
        Property property = propertyRepository.findById(propertyId).get();
        review.setProperty(property);
        System.out.println(property.getCity());
        review.setAppUser(user);
        Review saved = reviewRepository.save(review);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @GetMapping("/reviews")
    public List<Review> getUserReviews(
            @AuthenticationPrincipal AppUser user
    ){
        return reviewRepository.findByAppUser(user);
    }
}
