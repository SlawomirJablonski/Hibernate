package com.kodilla.hibernate.manytomany.facade;

import com.kodilla.hibernate.manytomany.Company;
import com.kodilla.hibernate.manytomany.Employee;
import org.junit.After;
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
public class DBTestSuite {
    @Autowired
    private DataBaseFacade dataBaseFacade;

    private Company c1 = new Company("coca-cola");
    private Company c2 = new Company("colgate");
    private Company c3 = new Company("coccodrillo");

    private Employee e1 = new Employee("Hubert", "Głowacki");
    private Employee e2 = new Employee("Magdalena", "Kamińska");
    private Employee e3 = new Employee("Michał", "Mazurkiewicz");

    @Test
    public void shouldFindCompanyWithNamePart() throws SearchingException {
        //Given
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

        dataBaseFacade.saveCompany(c1);
        dataBaseFacade.saveCompany(c2);
        dataBaseFacade.saveCompany(c3);

        //When
        String searchKey = "lo";
        List<Company> companies = new ArrayList<>();
        try{
            companies = dataBaseFacade.findCompanyWithNamePart(searchKey);
        }catch (SearchingException e){

        }

        //Then
        Assert.assertEquals(1, companies.size());
        Assert.assertEquals("coccodrillo",companies.get(0).getName());
    }

    @Test
    public void schouldFindEmployeeWithNamePart() throws SearchingException {
        //Given
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

        dataBaseFacade.saveEmployee(e1);
        dataBaseFacade.saveEmployee(e2);
        dataBaseFacade.saveEmployee(e3);

        //When
        String searchKey = "ńska";
        List<Employee> employes = new ArrayList<>();
        try{
            employes = dataBaseFacade.findEmployeeWithNamePart(searchKey);
        }catch (SearchingException e){

        }

        Employee firstMatchingEmployee = employes.get(0);

        //Then
        Assert.assertEquals("Magdalena", firstMatchingEmployee.getFirstname());
        Assert.assertEquals(1, employes.size());
    }

    @After
    public void cleanUp() {
        //CleanUp
        try {
            dataBaseFacade.deleteCompany(c1);
            dataBaseFacade.deleteCompany(c2);
            dataBaseFacade.deleteCompany(c3);
        } catch (Exception e) {
            //do nothing
        }

        try {
            dataBaseFacade.deleteEmployee(e1);
            dataBaseFacade.deleteEmployee(e2);
            dataBaseFacade.deleteEmployee(e3);
        } catch (Exception e) {
            //do nothing
        }
    }
}
