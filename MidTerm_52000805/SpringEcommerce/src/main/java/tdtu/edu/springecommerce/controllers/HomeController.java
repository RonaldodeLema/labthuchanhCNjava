package tdtu.edu.springecommerce.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tdtu.edu.springecommerce.models.Category;
import tdtu.edu.springecommerce.models.Product;
import tdtu.edu.springecommerce.models.User;
import tdtu.edu.springecommerce.services.impservices.CategoryServiceImp;
import tdtu.edu.springecommerce.services.impservices.ProductServiceImp;
import tdtu.edu.springecommerce.services.impservices.UserServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private CategoryServiceImp categoryServiceImp;
    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/")
    public String goHomePage() {
        return "redirect:/home-page";
    }
    @GetMapping("/home-page")
    public String getAllCateAndProd(Model model) {
        Iterable<Product> products = productServiceImp.findAll();
        Iterable<Category> categories = categoryServiceImp.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index";
    }
    @GetMapping("/product")
    public String goProduct() {
        return "redirect:/";
    }
    @GetMapping("/product/{id}")
    public String goProduct(@PathVariable("id") String id) {
        return "redirect:/";

    }
    @GetMapping("/product/")
    public String goProductNull() {
        return "redirect:/";
    }

    @GetMapping("/product/detail/{id}")
    public String getDetail(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Product product = productServiceImp.findByID(id);
            model.addAttribute("product", product);

            return "product-details";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }
    @GetMapping("/product/cart")
    public String goCart(Model model, HttpServletRequest request) {
        List<Product> products = (List<Product>) request.getSession().getAttribute("sessionProducts");
        if (products == null) {
            products = new ArrayList<>();
            request.getSession().setAttribute("sessionProducts", products);
        }
        model.addAttribute("products",products);
        return "cart";
    }

    @GetMapping("/product/cart/add/{id}")
    public String addToCart(@PathVariable("id") Long id,Model model, RedirectAttributes ra, HttpServletRequest request) {
        try {
            List<Product> products = (List<Product>) request.getSession().getAttribute("sessionProducts");
            Product product = productServiceImp.findByID(id);
            if (products == null) {
                products = new ArrayList<>();
                request.getSession().setAttribute("sessionProducts", products);
            }
            products.add(product);
            request.getSession().setAttribute("sessionProducts", products);
            model.addAttribute("products",products);
            return "redirect:/product/cart";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }
    @GetMapping("/product/cart/delete/{id}")
    public String removeToCart(@PathVariable("id") Long id,Model model, RedirectAttributes ra, HttpServletRequest request) {
        try {
            List<Product> products = (List<Product>) request.getSession().getAttribute("sessionProducts");
            Product product = productServiceImp.findByID(id);
            products.removeIf(i -> Objects.equals(i.getId(), product.getId()));
            HttpSession session = request.getSession();
            session.removeAttribute("sessionProducts");
            session.setAttribute("sessionProducts", products);
            request.getSession().setAttribute("sessionProducts", products);
            model.addAttribute("products",products);
            return "redirect:/product/cart";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/product/search")
    public String goSearch(Model model,@RequestBody String request) {
        String text = request.split("txtSearch=")[1];
        Iterable<Product> products = productServiceImp.searchAdvance(text);
        model.addAttribute("products", products);
        return "search";
    }
    @GetMapping("/auth")
    public String goAuth(Model model, HttpServletRequest request,RedirectAttributes ra) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    model.addAttribute("remUsername", cookie.getValue());
                }
                if (cookie.getName().equals("password")) {
                    model.addAttribute("remPassword", cookie.getValue());
                }
            }
        }
        if(session.getAttribute("userLogin")!=null){
            ra.addFlashAttribute("message", "Login success!");
            return "redirect:/";
        }
        model.addAttribute("user",new User());
        return "login";
    }
    @PostMapping("/auth/login")
    public String goLogin(User user, RedirectAttributes ra, HttpServletRequest request, HttpServletResponse response) {
        User userValid = userServiceImp.userLoginValid(user);
        if(userValid!=null){
            HttpSession session = request.getSession();
            Cookie ck = new Cookie("username",user.getUsername());
            Cookie ck1 = new Cookie("password",user.getPassword());
            response.addCookie(ck);
            response.addCookie(ck1);
            session.setAttribute("userLogin",user);
        }
        else ra.addFlashAttribute("message", "Password or Username invalid!");
        return "redirect:/auth";
    }
    @GetMapping("/auth/login")
    public String goLogin() {
        return "redirect:/auth";
    }

    @GetMapping("/contact")
    public String goContact() {
        return "contact-us";
    }

    @GetMapping("/check-out")
    public String goCheckOut() {
        return "checkout";
    }
    @GetMapping("/log-out")
    public String goLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
