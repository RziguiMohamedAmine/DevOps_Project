package tn.esprit.devops_project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.util.*;

public class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private InvoiceDetailRepository invoiceDetailRepository;

    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceService = new InvoiceServiceImpl(
                invoiceRepository, operatorRepository, invoiceDetailRepository, supplierRepository
        );
    }

    @Test
    public void testRetrieveInvoice() {

        Invoice simulatedInvoice = new Invoice();
        simulatedInvoice.setIdInvoice(1L);


        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(simulatedInvoice));

        Invoice retrievedInvoice = invoiceService.retrieveInvoice(1L);


        assertEquals(1L, retrievedInvoice.getIdInvoice());
    }

    @Test
    public void testCancelInvoice() {

        Invoice simulatedInvoice = new Invoice();
        simulatedInvoice.setIdInvoice(2L);
        simulatedInvoice.setArchived(false);


        when(invoiceRepository.findById(2L)).thenReturn(Optional.of(simulatedInvoice));

        invoiceService.cancelInvoice(2L);


    }


/*
    @Test
    public void testGetInvoicesBySupplier() {
        Long idSupplier = 1L;


        List<Invoice> simulatedInvoices = Arrays.asList(new Invoice(), new Invoice());

        Supplier supplier = new Supplier();
        supplier.setIdSupplier(idSupplier);

        when(supplierRepository.findById(idSupplier)).thenReturn(Optional.of(supplier));

        supplier.setInvoices(new HashSet<>(simulatedInvoices));

        List<Invoice> retrievedInvoices = invoiceService.getInvoicesBySupplier(idSupplier);

        assertEquals(simulatedInvoices.size(), retrievedInvoices.size());
    }*/
    @Test
    public void testRetrieveAllInvoice() {
        List<Invoice> mockInvoices = new ArrayList<>();
        mockInvoices.add(new Invoice());
        mockInvoices.add(new Invoice());
        when(invoiceRepository.findAll()).thenReturn(mockInvoices);

        List<Invoice> retrievedStocks = invoiceService.retrieveAllInvoices();
        Assertions.assertThat(retrievedStocks).hasSize(2);
    }
    @Test
    public void testGetTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date();
        Date endDate = new Date();
        float expectedTotalAmount = 100.0f;

        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(expectedTotalAmount);

        float actualTotalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        assertEquals(expectedTotalAmount, actualTotalAmount, 0.01);
    }


}
