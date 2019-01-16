package com.kodilla.hibernate.invoice.dao;

import com.kodilla.hibernate.invoice.Invoice;
import com.kodilla.hibernate.invoice.Item;
import com.kodilla.hibernate.invoice.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceDaoTestSuite {
    @Autowired
    InvoiceDao invoiceDao;

    @Test
    public void testInvoiceDaoSave(){
        //Given
        Product i7 = new Product("Apple iPhone 7");
        Product S60 = new Product("Cat S60 32GB Dual Sim");

        Item orlen_i7 = new Item(i7,new BigDecimal(1575),20);
        Item orlen_s60 = new Item(S60,new BigDecimal(1290),12);

        Invoice orlenInvoice = new Invoice("155/2019");

        orlen_i7.setInvoice(orlenInvoice);
        orlen_s60.setInvoice(orlenInvoice);

        List<Item> itemsForOrlen = new ArrayList<>();
        itemsForOrlen.add(orlen_i7);
        itemsForOrlen.add(orlen_s60);

        orlenInvoice.setItems(itemsForOrlen);

        //When
        invoiceDao.save(orlenInvoice);
        int invoiceId = orlenInvoice.getId();
        int itemsSize = orlenInvoice.getItems().size();
        Invoice invoiceFromRepo = invoiceDao.findById(invoiceId);
        Item iOne = invoiceFromRepo.getItems().get(0);
        Item iTwo = invoiceFromRepo.getItems().get(1);

        //Then
        Assert.assertNotEquals(0, invoiceId);
        Assert.assertEquals(2,itemsSize);
        Assert.assertEquals("155/2019", invoiceFromRepo.getNumber());
        Assert.assertEquals(31500,iOne.getValue().intValueExact());
        Assert.assertEquals(15480,iTwo.getValue().intValueExact());

        //CleanUp
        /*try {
            invoiceDao.delete(invoiceId);
        } catch (Exception e) {
            //do nothing
        }*/
    }
}
