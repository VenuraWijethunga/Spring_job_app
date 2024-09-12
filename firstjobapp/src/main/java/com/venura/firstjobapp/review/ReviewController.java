package com.venura.firstjobapp.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{comapanyId}")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long comapanyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(comapanyId), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(@PathVariable Long comapanyId ,@RequestBody Review review) {

        boolean isReviewSaved = reviewService.addReview(comapanyId, review);

        if (isReviewSaved)
        return new ResponseEntity<>("Review Added Successfully",HttpStatus.OK);

        else
            return new ResponseEntity<>("Review Not Added Successfully",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long comapanyId,
                                            @PathVariable Long reviewId) {
        return new ResponseEntity<>( reviewService.getReview(comapanyId, reviewId),HttpStatus.OK);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long comapanyId ,
                                               @PathVariable Long reviewId ,
                                               @RequestBody Review review) {
        boolean isReviewUpdated = reviewService.updateReview(comapanyId,reviewId,review);

        if (isReviewUpdated)
        return new ResponseEntity<>("Review Updated Successfully",HttpStatus.OK);

        else
            return new ResponseEntity<>("Review Not Updated Successfully",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long comapanyId ,@PathVariable Long reviewId) {

        boolean isReviewDeleted = reviewService.deleteReview(comapanyId,reviewId);

        if (isReviewDeleted)
            return new ResponseEntity<>("Review Deleted Successfully",HttpStatus.OK);

        else
            return new ResponseEntity<>("Review Not Deleted Successfully",HttpStatus.NOT_FOUND);
    }
}
