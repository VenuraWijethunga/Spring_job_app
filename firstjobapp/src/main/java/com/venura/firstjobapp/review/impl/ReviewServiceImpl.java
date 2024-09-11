package com.venura.firstjobapp.review.impl;

import com.venura.firstjobapp.company.Company;
import com.venura.firstjobapp.company.CompanyService;
import com.venura.firstjobapp.review.Review;
import com.venura.firstjobapp.review.ReviewRepository;
import com.venura.firstjobapp.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private CompanyService companyService;


    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId) ;
        return reviews;
    }

    @Override
    public void addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if(company == null) {
            review.setCompany(company);
            reviewRepository.save(review);
        }
    }
}
