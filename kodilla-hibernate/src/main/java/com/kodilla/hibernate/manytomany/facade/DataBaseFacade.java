package com.kodilla.hibernate.manytomany.facade;

import com.kodilla.hibernate.manytomany.Company;
import com.kodilla.hibernate.manytomany.Employee;
import com.kodilla.hibernate.manytomany.dao.CompanyDao;
import com.kodilla.hibernate.manytomany.dao.EmployeeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataBaseFacade {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private EmployeeDao employeeDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseFacade.class);

    public void saveCompany(Company company){
        companyDao.save(company);
    }
    public void saveEmployee(Employee employee){
        employeeDao.save(employee);
    }
    public void deleteCompany(Company company){
        companyDao.delete(company);
    }
    public void deleteEmployee(Employee employee){
        employeeDao.delete(employee);
    }


    public List<Company> findCompanyWithNamePart(String arg) throws SearchingException {
        LOGGER.info("Searching for company with searchkey: " + arg);
        List<Company> companies = companyDao.retrieveCompanyWithPartName(arg);
        if(companies.size()<1) {
            LOGGER.error("No matching company for searchkey: " + arg);
            throw new SearchingException(SearchingException.ERR_COMPANY_NOT_FOUND);
        } else {
            LOGGER.info("List of comapnies" + companies.stream()
                            .map(Company::getName)
                            .collect(Collectors.joining(",")
                            ));
        }
        return companies;
    }

    public List<Employee> findEmployeeWithNamePart(String arg) throws SearchingException {
        LOGGER.info("Searching for employee with searchkey: " + arg);
        List<Employee> employes = employeeDao.retrieveEmployeeWithPartName(arg);
        if(employes.size()<1) {
            LOGGER.error("No matching employee for searchkey: " + arg);
            throw new SearchingException(SearchingException.ERR_EMPLOYEE_NOT_FOUND);
        } else {
            LOGGER.info("List of employes" + employes.stream()
                    .map(employee -> String.format("%s %s", employee.getFirstname(), employee.getLastname()))
                    .collect(Collectors.joining(",")
                    ));
        }
        return employes;
    }
}