package tdtu.edu.ex03.Servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import tdtu.edu.ex03.Module.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
@WebServlet(name = "ProductServlet", value = "/ProductServlet")
public class ProductServlet extends HttpServlet {
    private final List<Product> productList = new ArrayList<>();

    public void init() {
        String[] names ={"Thit heo","Thit bo","Thit ga"};
        for(int i = 0;i<names.length;i++){
            productList.add(new Product(i+1,names[i],50000*(i+1)));
        }
    }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject obj = new JSONObject();
            try {
                if (request.getParameter("id") != null) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Product p = getProductById(id);
                    if (p == null) {
                        obj.put("id", 0);
                        obj.put("message", "Product not found");
                        obj.put("data", "");
                    } else {
                        obj.put("id", 1);
                        obj.put("message", "Product found");
                        obj.put("data", p);
                    }
                } else {
                    obj.put("id", 1);
                    obj.put("message", "All Products");
                    obj.put("data", new Gson().toJson(productList));
                }

            } catch (Exception e) {
                obj.put("id", 0);
                obj.put("message", e.getMessage());
                obj.put("data", "");
            } finally {
                out.println(obj);
                out.close();
            }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));

            if (getProductById(id) == null) {
                Product p = new Product(id, name, price);
                productList.add(p);
            } else {
                obj.put("id", 0);
                obj.put("message", "Product with same ID exists");
                obj.put("data", "");
            }
        } catch (Exception e) {
            obj.put("id", 0);
            obj.put("message", e.getMessage());
            obj.put("data", "");
        } finally {
            out.println(obj);
            out.close();
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        if(id.equals("")||name.equals("")||price.equals("")){
            return;
        }
        Product product = getProductById(Integer.parseInt(id));
        int priceNum = Integer.parseInt(price);
        if(product!= null){
            for(Product prod: productList){
                if(prod.id == Integer.parseInt(id)){
                    prod.name = name;
                    prod.price = priceNum;
                    break;
                }
                obj.put("id", 1);
                obj.put("message", "Product updated successfully");
                obj.put("data", "");
            }
            out.println(obj);
            out.close();
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        //Check if the id exists in the database
        Product product = getProductById(id);
        if (product == null) {
            //Return error message
            String message = "Product id " + id + " does not exist";
            String data = null;
            int id = 0;
            returnErrorResponse(resp, id, message, data);
        }

        //Delete product
        deleteProduct(product);

        //Return success message
        String message = "Product deleted successfully";
        String data = null;
        int id = 1;
        returnResponse(resp, id, message, data);

    }
    private Product getProductById(int id) {
        for (Product prod : productList){
            if(prod.id==id){
                return prod;
            }
        }
        return null;
    }

    public void destroy() {
    }
}
