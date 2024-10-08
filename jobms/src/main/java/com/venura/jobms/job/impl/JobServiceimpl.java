package com.venura.jobms.job.impl;


import com.venura.jobms.job.Job;
import com.venura.jobms.job.JobRepository;
import com.venura.jobms.job.JobService;
import com.venura.jobms.job.dto.JobWithCompanyDTO;
import com.venura.jobms.job.external.Company;
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

    public JobServiceimpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //Create an own method to connect Job and Company with DTO(Data Transfer Object)
    private JobWithCompanyDTO convertToDto(Job job) {

            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);

            RestTemplate restTemplate = new RestTemplate();

            Company company = restTemplate.getForObject("http://localhost:8081/companies/" +job.getCompanyId(),
                    Company.class);
            jobWithCompanyDTO.setCompany(company);

            return jobWithCompanyDTO;


    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
       return jobRepository.findById(id).orElse(null);
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
