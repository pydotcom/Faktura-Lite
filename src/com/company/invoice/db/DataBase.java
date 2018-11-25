package com.company.invoice.db;

import com.company.invoice.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.invoice.dictionaries.Dictionary.*;
import static com.company.invoice.dictionaries.Errors.SELECT_DB_ERROR;

public class DataBase {
    private Connection conn;
    private Statement statement;

    /**
     * Open connection to database
     * @return false when there is a problem to connect with database and true when connected with database
     * @constant CONNECTION_STRING a database path from Dictionary.java
     */
    public boolean open() {
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Couldn't connect to database" + e.getMessage());
            return false;
        }
    }

    /**
     * Close connection to database
     *
     */
    public void close() {
        try {
            if(conn != null)
                conn.close();
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't close connection:" + e.getMessage());
        }
    }

    /**
     * Adding new Customer to database
     * Customer id is automatically incremented (send NULL to database)
     * @param customer transfer data to table customer from database
     */
    public void addStatement(Customer customer) {
        try(Statement statement = conn.createStatement()){

            statement.execute("INSERT INTO " + TABLE_CUSTOMER +
                    " (" + COLUMN_CUSTOMER_ID + ", " +
                    COLUMN_CUSTOMER_NAME + ", " +
                    COLUMN_CUSTOMER_CITY + ", " +
                    COLUMN_CUSTOMER_STREET + ", " +
                    COLUMN_CUSTOMER_POST_CODE + ", " +
                    COLUMN_CUSTOMER_NIP +
                    ")" +
                    "VALUES(NULL" + ", '" + customer.getName()  +
                            "', '" + customer.getCity() + "', '"  + customer.getStreet() + "', '"  +
                            customer.getPostCode() + "', '" + customer.getNIP() + "')");
        }
        catch (SQLException e)
        {
            System.out.println("Add statement ERROR: " + e.getMessage());
        }
    }
    /**
     * Adding new User to database
     * Customer id is automatically incremented (send NULL to database)
     * @param user transfer data to table customer from database
     */
    public void addStatement(User user) {
        try(Statement statement = conn.createStatement()){

            statement.execute("INSERT INTO " + TABLE_USER +
                    " (" + COLUMN_USER_ID + ", " +
                    COLUMN_USER_NAME + ", " +
                    COLUMN_USER_CITY + ", " +
                    COLUMN_USER_STREET + ", " +
                    COLUMN_USER_POST_CODE + ", " +
                    COLUMN_USER_NIP +
                    ")" +
                    "VALUES(NULL" + ", '" + user.getName()  +
                    "', '" + user.getCity() + "', '"  + user.getStreet() + "', '"  +
                    user.getPostCode() + "', '" + user.getNIP() + "')");
        }
        catch (SQLException e)
        {
            System.out.println("Add statement ERROR: " + e.getMessage());
        }
    }

    /**
     * Adding new Product to database
     * @param product transfer data to table customer from database
     */
    public void addStatement(Product product) {
        try(Statement statement = conn.createStatement()){

            statement.execute("INSERT INTO " + TABLE_PRODUCT +
                    " (" + COLUMN_PRODUCT_ID + ", " +
                    COLUMN_PRODUCT_NAME + ", " +
                    COLUMN_PRODUCT_PRICE_BRUTTO + ", " +
                    COLUMN_PRODUCT_PRICE_NETTO+ ", " +
                    COLUMN_PRODUCT_VAT +
                    ")" +
                    "VALUES(NULL" +  ", '" + product.getName() + "', " +
                    product.getDBPriceBrutto() + ", " + product.getDBPriceNetto() + ", " +
                    product.getVat() + ")");
        }
        catch (SQLException e)
        {
            System.out.println("Add statement ERROR: " + e.getMessage());
        }
    }

    /**
     * Adding new Invoice to database
     * @param invoice transfer data to table invoice from database
     */
    public void addStatement(Invoice invoice) {
        try(Statement statement = conn.createStatement()){

            statement.execute("INSERT INTO " + TABLE_INVOICE +
                    " (" + COLUMN_INVOICE_ID + ", " +
                    COLUMN_INVOICE_CUSTOMER_ID + ", " +
                    COLUMN_INVOICE_USER_ID + ", " +
                    COLUMN_INVOICE_INVOICE_DATE + ", " +
                    COLUMN_INVOICE_ISSUE_DATE +
                    ")" +
                    "VALUES(NULL" +  ", " + invoice.getCustomerId() + ", " +
                    invoice.getUserId() + ", '" + invoice.getInvoiceDate() + "', '" +
                    invoice.getIssueDate() + "')");
        }
        catch (SQLException e)
        {
            System.out.println("Add statement ERROR: " + e.getMessage());
        }
    }

    public void addStatement(Item item) {
        try(Statement statement = conn.createStatement()){

            statement.execute("INSERT INTO " + TABLE_ITEM +
                    " (" + COLUMN_ITEM_ID + ", " +
                    COLUMN_ITEM_INVOICE_ID + ", " +
                    COLUMN_ITEM_NAME + ", " +
                    COLUMN_ITEM_QUANTITY + ", " +
                    COLUMN_ITEM_PRICE_BRUTTO + ", " +
                    COLUMN_ITEM_PRICE_NETTO + ", " +
                    COLUMN_ITEM_VAT +
                    ")" +
                    "VALUES(NULL" +  ", " + item.getInvoiceId() + ", '" +
                    item.getName() + "', " + item.getQuantity() + ", " +
                    item.getDBPriceBrutto() + ", " + item.getDBPriceNetto() + ", " +
                    item.getVat() + ")");
        }
        catch (SQLException e)
        {
            System.out.println("Add statement ERROR: " + e.getMessage());
        }
    }

    /**
     * Downloading Customers List from database.
     * @return customerGroup with all db customer list or NULL when there is problem with database
     */
    public List<Customer> downloadCustomers() {
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_CUSTOMER)){

            List<Customer> customerList = new ArrayList<>();
            while(result.next())
            {
                Customer customer = new Customer();
                customer.setId(result.getInt(COLUMN_CUSTOMER_ID));
                customer.setName(result.getString(COLUMN_CUSTOMER_NAME));
                customer.setCity(result.getString(COLUMN_CUSTOMER_CITY));
                customer.setStreet(result.getString(COLUMN_CUSTOMER_STREET));
                customer.setPostCode(result.getString(COLUMN_CUSTOMER_POST_CODE));
                customer.setNIP(result.getString(COLUMN_CUSTOMER_NIP));

                customerList.add(customer);
            }

            return customerList;
        }
        catch(SQLException e)
        {
            System.out.println(SELECT_DB_ERROR + e.getMessage());
            return null;
        }
    }

    /**
     * Downloading Users List from database.
     * @return userGroup with all db users list or NULL when there is problem with database
     */
    public List<User> downloadUsers() {
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_USER)){

            List<User> userList = new ArrayList<>();
            while(result.next())
            {
                User user = new User();
                user.setId(result.getInt(COLUMN_USER_ID));
                user.setName(result.getString(COLUMN_USER_NAME));
                user.setCity(result.getString(COLUMN_USER_CITY));
                user.setStreet(result.getString(COLUMN_USER_STREET));
                user.setPostCode(result.getString(COLUMN_USER_POST_CODE));
                user.setNIP(result.getString(COLUMN_USER_NIP));

                userList.add(user);
            }

            return userList;
        }
        catch(SQLException e)
        {
            System.out.println(SELECT_DB_ERROR + e.getMessage());
            return null;
        }
    }

    /**
     * Downloading Products List from database.
     * @return
     */
    public List<Product> downloadProducts() {
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_PRODUCT)){

            List<Product> productList = new ArrayList<>();
            while(result.next())
            {
                Product product = new Product();
                product.setId(result.getInt(COLUMN_PRODUCT_ID));
                product.setName(result.getString(COLUMN_PRODUCT_NAME));
                product.setDBPriceBrutto(result.getInt(COLUMN_PRODUCT_PRICE_BRUTTO));
                product.setDBPriceNetto(result.getInt(COLUMN_PRODUCT_PRICE_NETTO));
                product.setVat(result.getInt(COLUMN_PRODUCT_VAT));
                productList.add(product);
            }

            return productList;
        }
        catch(SQLException e)
        {
            System.out.println(SELECT_DB_ERROR + e.getMessage());
            return null;
        }
    }

    public List<Invoice> downloadInvoices() {
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_INVOICE)){

            List<Invoice> invoiceList = new ArrayList<>();
            while(result.next())
            {
                Invoice invoice = new Invoice();
                invoice.setId(result.getInt(COLUMN_CUSTOMER_ID));
                invoice.setCustomerId(result.getInt(COLUMN_INVOICE_CUSTOMER_ID));
                invoice.setUserId(result.getInt(COLUMN_INVOICE_USER_ID));
                invoice.setInvoiceDate(result.getString(COLUMN_INVOICE_INVOICE_DATE));
                invoice.setIssueDate(result.getString(COLUMN_INVOICE_ISSUE_DATE));

                invoiceList.add(invoice);
            }

            return invoiceList;
        }
        catch(SQLException e)
        {
            System.out.println(SELECT_DB_ERROR + e.getMessage());
            return null;
        }
    }
}