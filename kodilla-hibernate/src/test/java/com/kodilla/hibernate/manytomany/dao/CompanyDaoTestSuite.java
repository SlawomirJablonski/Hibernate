package com.kodilla.hibernate.manytomany.dao;

import com.kodilla.hibernate.manytomany.Company;
import com.kodilla.hibernate.manytomany.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyDaoTestSuite {
    @Autowired
    CompanyDao companyDao;
    @Autowired
    EmployeeDao employeeDao;

    @Test
    public void testSaveManyToMany() {
        //Given
        Employee johnSmith = new Employee("John", "Smith");
        Employee stephanieClarckson = new Employee("Stephanie", "Clarckson");
        Employee lindaKovalsky = new Employee("Linda", "Kovalsky");

        Company softwareMachine = new Company("Software Machine");
        Company dataMaesters = new Company("Data Maesters");
        Company greyMatter = new Company("Grey Matter");

        softwareMachine.getEmployees().add(johnSmith);
        dataMaesters.getEmployees().add(stephanieClarckson);
        dataMaesters.getEmployees().add(lindaKovalsky);
        greyMatter.getEmployees().add(johnSmith);
        greyMatter.getEmployees().add(lindaKovalsky);

        johnSmith.getCompanies().add(softwareMachine);
        johnSmith.getCompanies().add(greyMatter);
        stephanieClarckson.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(greyMatter);

        //When
        companyDao.save(softwareMachine);
        int softwareMachineId = softwareMachine.getId();
        companyDao.save(dataMaesters);
        int dataMaestersId = dataMaesters.getId();
        companyDao.save(greyMatter);
        int greyMatterId = greyMatter.getId();

        //Then
        Assert.assertNotEquals(0, softwareMachineId);
        Assert.assertNotEquals(0, dataMaestersId);
        Assert.assertNotEquals(0, greyMatterId);

        //CleanUp
        //try {
        //    companyDao.delete(softwareMachineId);
        //    companyDao.delete(dataMaestersId);
        //    companyDao.delete(greyMatterId);
        //} catch (Exception e) {
        //    //do nothing
        //}
    }

    @Test
    public void testNamedNativeQuery() {
        //Given
        Company c1 = new Company("coca-cola");
        Company c2 = new Company("colgate");
        Company c3 = new Company("coccodrillo");

        Employee e1 = new Employee("Hubert", "Głowacki");
        Employee e2 = new Employee("Magdalena", "Kamińska");
        Employee e3 = new Employee("Michał", "Mazurkiewicz");

        List<Company> e1Companies = new ArrayList<>();
        e1Companies.add(c1);
        e1Companies.add(c2);
        e1.setCompanies(e1Companies);

        List<Company> e2Companies = new ArrayList<>();
        e2Companies.add(c1);
        e2.setCompanies(e2Companies);

        List<Company> e3Companies = new ArrayList<>();
        e3Companies.add(c1);

        List<Employee> cocacolaEmployees = new ArrayList<>();
        cocacolaEmployees.add(e1);
        cocacolaEmployees.add(e2);
        cocacolaEmployees.add(e3);
        c1.setEmployees(cocacolaEmployees);

        List<Employee> colgateEmployees = new ArrayList<>();
        colgateEmployees.add(e1);
        c2.setEmployees(colgateEmployees);

        companyDao.save(c1);
        companyDao.save(c2);
        companyDao.save(c3);

        //When
        String quest = "coc";
        String quest2 = "col";
        List<Company> companies = companyDao.retrieveCompanyWithNameBeginning(quest);
        List<Company> companies2 = companyDao.retrieveCompanyWithNameBeginning(quest2);
        Company firstCompanyOnTheList = companies.get(0);
        Company firstCompanyOnTheList2 = companies2.get(0);
        Employee firstEmployee = firstCompanyOnTheList.getEmployees().get(0);
        Employee firstEmployee2 = firstCompanyOnTheList2.getEmployees().get(0);

        //Then
        Assert.assertEquals("Głowacki",firstEmployee.getLastname());
        Assert.assertEquals("Hubert",firstEmployee2.getFirstname());
        Assert.assertEquals(1,companies.size());

        //CleanUp
        try {
            companyDao.delete(c1);
            companyDao.delete(c2);
            companyDao.delete(c3);
        } catch (Exception e) {
            //do nothing
        }
    }
    @Test
    public void testNamedQuery() {
        //Given
        Company c1 = new Company("coca-cola");
        Company c2 = new Company("colgate");
        Company c3 = new Company("coccodrillo");

        Employee e1 = new Employee("Hubert", "Głowacki");
        Employee e2 = new Employee("Magdalena", "Kamińska");
        Employee e3 = new Employee("Michał", "Mazurkiewicz");

        List<Company> e1Companies = new ArrayList<>();
        e1Companies.add(c1);
        e1Companies.add(c2);
        e1.setCompanies(e1Companies);

        List<Company> e2Companies = new ArrayList<>();
        e2Companies.add(c1);
        e2.setCompanies(e2Companies);

        List<Company> e3Companies = new ArrayList<>();
        e3Companies.add(c1);

        List<Employee> cocacolaEmployees = new ArrayList<>();
        cocacolaEmployees.add(e1);
        cocacolaEmployees.add(e2);
        cocacolaEmployees.add(e3);
        c1.setEmployees(cocacolaEmployees);

        List<Employee> colgateEmployees = new ArrayList<>();
        colgateEmployees.add(e1);
        c2.setEmployees(colgateEmployees);

        employeeDao.save(e1);
        employeeDao.save(e2);
        employeeDao.save(e3);

        //When
        String matchName = "Kamińska";
        List<Employee> employeesWithMatchingName = employeeDao.retrieveEmployeeWithLastname(matchName);
        Employee firstMatchingEmployee = employeesWithMatchingName.get(0);

        //Then
        Assert.assertEquals("Magdalena",firstMatchingEmployee.getFirstname());
        Assert.assertEquals(1,employeesWithMatchingName.size());

        //CleanUp
        try {
            employeeDao.delete(e1);
            employeeDao.delete(e2);
            employeeDao.delete(e3);
        } catch (Exception e) {
            //do nothing
        }
    }
}
