package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import com.codegym.service.CustomerServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Controller: dieu huong moi thao tac quan ly khach hang qua tham so "action".
 * GET  /customers                    -> danh sach
 * GET  /customers?action=create      -> form them moi
 * GET  /customers?action=edit&id=1   -> form sua
 * GET  /customers?action=delete&id=1 -> trang xac nhan xoa
 * GET  /customers?action=view&id=1   -> xem chi tiet
 * POST /customers?action=create|edit|delete -> xu ly du lieu form
 */
@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createCustomer(request, response);
                break;
            case "edit":
                updateCustomer(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/customers");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showCustomerPage(request, response, "/customer/edit.jsp");
                break;
            case "delete":
                showCustomerPage(request, response, "/customer/delete.jsp");
                break;
            case "view":
                showCustomerPage(request, response, "/customer/view.jsp");
                break;
            default:
                listCustomers(request, response);
                break;
        }
    }

    // ===== Cac ham xu ly GET (hien thi trang) =====

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Customer> customers = customerService.findAll();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/customer/list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/customer/create.jsp").forward(request, response);
    }

    /** Dung chung cho edit / delete / view: tim khach hang theo id roi chuyen toi trang tuong ung. */
    private void showCustomerPage(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        Customer customer = findCustomer(request);
        if (customer == null) {
            showNotFound(request, response);
            return;
        }
        request.setAttribute("customer", customer);
        request.getRequestDispatcher(page).forward(request, response);
    }

    // ===== Cac ham xu ly POST (thay doi du lieu) =====

    private void createCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        // Sinh id ngau nhien, tranh trung voi id da ton tai
        int id;
        do {
            id = ThreadLocalRandom.current().nextInt(1, 10000);
        } while (customerService.findById(id) != null);

        customerService.save(new Customer(id, name, email, address));
        request.setAttribute("message", "New customer was created");
        request.getRequestDispatcher("/customer/create.jsp").forward(request, response);
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Customer customer = findCustomer(request);
        if (customer == null) {
            showNotFound(request, response);
            return;
        }
        customer.setName(request.getParameter("name"));
        customer.setEmail(request.getParameter("email"));
        customer.setAddress(request.getParameter("address"));
        customerService.update(customer.getId(), customer);

        request.setAttribute("customer", customer);
        request.setAttribute("message", "Customer information was updated");
        request.getRequestDispatcher("/customer/edit.jsp").forward(request, response);
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Customer customer = findCustomer(request);
        if (customer == null) {
            showNotFound(request, response);
            return;
        }
        customerService.remove(customer.getId());
        response.sendRedirect(request.getContextPath() + "/customers");
    }

    // ===== Ham tien ich =====

    /** Doc tham so "id" va tim khach hang; tra ve null neu id sai dinh dang hoac khong ton tai. */
    private Customer findCustomer(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            return customerService.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void showNotFound(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.getRequestDispatcher("/error-404.jsp").forward(request, response);
    }
}
