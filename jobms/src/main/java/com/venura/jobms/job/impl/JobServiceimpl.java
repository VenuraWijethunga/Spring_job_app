package com.venura.jobms.job.impl;


import com.venura.jobms.job.Job;
import com.venura.jobms.job.JobRepository;
import com.venura.jobms.job.JobService;
import com.venura.jobms.job.dto.JobDTO;
import com.venura.jobms.job.external.Company;
import com.venura.jobms.job.external.Review;
import com.venura.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceimpl  implements JobService {

    //private final List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
    private Long nextID= 1L;

    @Autowired
    RestTemplate restTemplate;

    public JobServiceimpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //Create an own method to connect Job and Company with DTO(Data Transfer Object)
    private JobDTO convertToDto(Job job) {

        //RestTemplate restTemplate = new RestTemplate();

            Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" +job.getCompanyId(),
                    Company.class);

            ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                    "http://REVIEW-SERVICE:8083/reviews?companyId=" +job.getCompanyId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {});

            List<Review> reviews = reviewResponse.getBody();

            JobDTO jobDTO = JobMapper.
                    mapToJobWithCompanyDTO(job,company,reviews);
            //jobDTO.setCompany(company);

            return jobDTO;


    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job=jobRepository.findById(id).orElse(null);
          return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

            if (jobOptional.isPresent()) {
                Job job = jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                jobRepository.save(job);

                return true;

        }
        return false;
    }


}
