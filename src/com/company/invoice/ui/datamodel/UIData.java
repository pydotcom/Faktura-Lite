package com.company.invoice.ui.datamodel;

import com.company.invoice.dto.Customer;
import com.company.invoice.dto.Invoice;
import com.company.invoice.dto.Item;
import com.company.invoice.dto.Payment;
import com.company.invoice.utils.CustomerUtils;
import com.company.invoice.utils.InvoiceUtils;
import com.company.invoice.utils.ItemUtils;
import com.company.invoice.utils.PaymentUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UIData {

    private static UIData instance = new UIData();

    private ObservableList<InvoiceModel> invoiceModels;
    private ObservableList<ContractorModel> contractorModels;
    private InvoiceUtils invoiceUtils;
    private CustomerUtils customerUtils;
    private ItemUtils itemUtils;
    private PaymentUtils paymentUtils;

    private UIData() {
        invoiceUtils = new InvoiceUtils();
        customerUtils = new CustomerUtils();
        itemUtils = new ItemUtils();
        paymentUtils = new PaymentUtils();
        invoiceModels = FXCollections.observableArrayList();
        contractorModels = FXCollections.observableArrayList();
    }

    public static UIData getInstance() {
        return instance;
    }

    public ObservableList<InvoiceModel> getInvoiceModels() {
        return invoiceModels;
    }

    public ObservableList<ContractorModel> getContractorModels() {
        return contractorModels;
    }

    public void addInvoiceModel() {

    }

    /**
     * Method download and prepare Invoice data to display it in UI
     */
    public void loadInvoiceTable() {
        List<Invoice> invoiceList = invoiceUtils.downloadInvoices();

        for (Invoice invoice : invoiceList) {
            InvoiceModel invoiceModel = new InvoiceModel();
            Customer customer = customerUtils.downloadCustomer(invoice.getCustomerId());
            List<Item> itemsList = itemUtils.downloadItems(invoice.getId());
            Payment payment = paymentUtils.downloadPayment(invoice.getPaymentId());

            invoiceModel.setInvoiceType(invoice.getInvoiceType());
            invoiceModel.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceModel.setIssueDate(invoice.getIssueDate());
            invoiceModel.setCustomerName(customer.getName());
            invoiceModel.setNettoValue(Double.toString(getNettoValue(itemsList)));
            invoiceModel.setBruttoValue(Double.toString(getBruttoValue(itemsList)));
            invoiceModel.setVatValue(Double.toString(getBruttoValue(itemsList) - getNettoValue(itemsList)));
            invoiceModel.setCurrency(payment.getCurrency());

            invoiceModels.add(invoiceModel);
        }
    }

    public void loadContractorTable() {
        List<Customer> customerList = customerUtils.downloadCustomers();
        for(Customer customer : customerList) {
            ContractorModel contractorModel = new ContractorModel();

            contractorModel.setName(customer.getName());
            contractorModel.setCity(customer.getCity());
            contractorModel.setStreet(customer.getStreet());
            contractorModel.setHouseNumber(Integer.toString(customer.getHouseNumber()));
            contractorModel.setApartmentNumber(Integer.toString(customer.getApartmentNumber()));
            contractorModel.setPostCode(customer.getPostCode());
            contractorModel.setNIP(customer.getNIP());

            contractorModels.add(contractorModel);
        }
    }

    private double getNettoValue(List<Item> itemsList) {
        double totalNettoValue = 0;
        for (Item item : itemsList) {
            totalNettoValue += item.getQuantity() * item.getPriceNetto();
        }
        return totalNettoValue;
    }

    private double getBruttoValue(List<Item> itemsList) {
        double totalBruttoValue = 0;
        for (Item item : itemsList) {
            totalBruttoValue += item.getQuantity() * item.getPriceBrutto();
        }
        return totalBruttoValue;
    }

}
