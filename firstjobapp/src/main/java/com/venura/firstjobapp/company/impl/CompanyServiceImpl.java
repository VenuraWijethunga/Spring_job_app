package com.venura.firstjobapp.company.impl;

import com.venura.firstjobapp.company.Company;
import com.venura.firstjobapp.company.CompanyRepository;
import com.venura.firstjobapp.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            companyToUpdate.setDescription(companyToUpdate.getDescription());
            companyToUpdate.setName(companyToUpdate.getName());
            companyToUpdate.setJobs(company.getJobs());

            companyRepository.save(companyToUpdate);

            return true;

        }else
        {
        return false;
        }
    }

}
