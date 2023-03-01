package com.example.lab05.Servlet;

import com.example.lab05.DAO.ProductDAO;
import com.example.lab05.Model.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/del")
public class DeleteProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    @Override
    public void init() {
        productDAO = ProductDAO.getInstance();
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("isLogout").equals("true")){
            HttpSession session = req.getSession();
            if (!session.isNew()) {
                session.invalidate();
            }
        }
        else {
            resp.sendRedirect("login");
        }
    }
        public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prodDelID = req.getParameter("prodDelID");
        if(prodDelID != null){
            productDAO.delete(Integer.parseInt(prodDelID));
        }
        List<Product> listProducts = productDAO.readAll();
        req.setAttribute("listProducts", listProducts);
        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req , resp);
    }
    public void destroy(){
        productDAO.close();
    }
}