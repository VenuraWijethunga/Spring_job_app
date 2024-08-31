package com.venura.firstjobapp.job;

  import org.springframework.boot.autoconfigure.batch.BatchProperties;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.*;

  import java.util.ArrayList;
import java.util.List;

@RestController
public class JobController {

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    private JobService jobService;

    @GetMapping("/jobs")
    public List<Job> findAll(){
        return jobService.findAll();
    }

    @PostMapping("/jobs")
    public String createJob(@RequestBody Job job){
        jobService.createJob(job);
        return "Job added sccesfully";
    }

    @GetMapping("/jobs/{id}")
    public Job getJobByIf(@PathVariable Long id){
        HttpStatus
        Job job = jobService.getJobById(id);
        if(job != null)
            return job;
        return new Job(1L,"testJob","TestJob", "2000","2000",
                "loc");
    }
}

