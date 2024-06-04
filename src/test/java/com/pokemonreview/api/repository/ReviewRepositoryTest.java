package com.pokemonreview.api.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview(){
        Review review = Review.builder().title("title").content("content").stars(5).build();
        
        Review savedReview = reviewRepository.save(review);
        
        assertNotNull(savedReview);
        assertTrue(savedReview.getId() > 0);
    }
    
    @Test
    public void ReviewRepository_GetAll_ReturnMoreThanOne(){
        Review review = Review.builder().title("title").content("content").stars(5).build();
        Review review2 = Review.builder().title("title").content("content").stars(5).build();
        
        reviewRepository.save(review);
        reviewRepository.save(review2);

        // Review savedReview = reviewRepository.findById(review.getId()).get();
        List<Review> reviewList = reviewRepository.findAll();

        assertNotNull(reviewList);
    }

    @Test
    public void ReviewRepository_FindById_ReturnSavedReview(){
        Review review = Review.builder().title("title").content("content").stars(5).build();
        
        Review savedReview = reviewRepository.save(review);
        
        Review reviewReturn = reviewRepository.findById(savedReview.getId()).get();

        assertNotNull(reviewReturn);
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnSavedReview(){
        Review review = Review.builder().title("title").content("content").stars(5).build();
        
       reviewRepository.save(review);
       
       Review review2 = reviewRepository.findById(review.getId()).get();
       
       review2.setTitle("aleksa");
       review2.setContent("aleksa");
       review2.setStars(2);
       
       Review updated = reviewRepository.save(review2);
        
        assertNotNull(updated);
        assertNotNull(updated.getContent());
        assertNotNull(updated.getTitle());
        assertNotNull(updated.getId());
    }

    
    @Test
    public void ReviewRepository_ReviewDelete_ReturnReviewIsEmpty(){
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        reviewRepository.delete(review);
        Optional<Review>  reviews= reviewRepository.findById(review.getId());

        assertTrue(reviews.isEmpty());

    }
}
