package tdtu.edu.springecommerce.controllers;

import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tdtu.edu.springecommerce.models.Cart;
import tdtu.edu.springecommerce.models.Category;
import tdtu.edu.springecommerce.models.Product;
import tdtu.edu.springecommerce.models.User;

import java.util.*;

@Controller
public class HomeController {
    public static final String baseUrl = "http://localhost:8080";
    @Autowired
    private HomeRestController homeRestController;

    @GetMapping("/")
    public String goHomePage() {
        return "redirect:/home-page";
    }

    @GetMapping("/home-page")
    public String getAllCateAndProd(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/api/get-all-cate-prod";
        Map<String, List<Object>> response = restTemplate.getForObject(url, Map.class);
        assert response != null;
        List<Product> products = (List<Product>) response.get("products").get(0);
        List<Category> categories = (List<Category>) response.get("categories").get(0);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index";
    }

    @PostMapping("/home-page")
    @ResponseBody
    public String goHomePage(Model model) {
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
        String api = "/api/product/find/" + id;
        String url = baseUrl + api;
        RestTemplate restTemplate = new RestTemplate();
        String strProduct = restTemplate.getForObject(url, String.class);
        try {
            Gson g = new Gson();
            Product product = g.fromJson(strProduct, Product.class);
            model.addAttribute("product", product);
            return "product-details";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/product/cart")
    public String goCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/auth";
        }
        User user = (User) session.getAttribute("userLogin");
        String api = "/api/cart/all-by-id/" + user.getId();
        String url = baseUrl + api;
        RestTemplate restTemplate = new RestTemplate();
        Iterable products = restTemplate.getForObject(url, Iterable.class);
        if (products == null) {
            products = new ArrayList<>();
        }
        model.addAttribute("products", products);
        return "cart";
    }

    @GetMapping("/product/cart/add/{id}")
    public String addToCart(@PathVariable("id") Long product_id, Model model, RedirectAttributes ra, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/auth";
        }
        User user = (User) session.getAttribute("userLogin");
        String api = "/api/cart/add";
        String url = baseUrl + api;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String requestJson = "{\"user_id\":\"" + user.getId() + "\",\"product_id\":\"" + product_id + "\"}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        String strCart = restTemplate.postForObject(url, entity, String.class);
        Gson gson = new Gson();
        Cart cart = gson.fromJson(strCart, Cart.class);
        System.out.println(cart);
//       get product by id
        api = "/api/cart/all-by-id/" + user.getId();
        url = baseUrl + api;
        restTemplate = new RestTemplate();
        try {
            Iterable products = restTemplate.getForObject(url, Iterable.class);
            if (products == null) {
                products = new ArrayList<>();
            }
            model.addAttribute("products", products);
            return "redirect:/product/cart";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/product/cart/delete/{id}")
    public String removeToCart(@PathVariable("id") Long id, Model model, RedirectAttributes ra, HttpServletRequest request) {
        try {
            List<Product> products = (List<Product>) request.getSession().getAttribute("sessionProducts");
            Product product = homeRestController.getDetails(id);
            products.removeIf(i -> Objects.equals(i.getId(), product.getId()));
            HttpSession session = request.getSession();
            session.removeAttribute("sessionProducts");
            session.setAttribute("sessionProducts", products);
            request.getSession().setAttribute("sessionProducts", products);
            model.addAttribute("products", products);
            return "redirect:/product/cart";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/product/search")
    public String goSearch(Model model, @RequestBody String request) {
        String text = request.split("txtSearch=")[1];
        String api = "/api/product/search/" + text;
        String url = baseUrl + api;
        RestTemplate restTemplate = new RestTemplate();
        Iterable products = restTemplate.getForObject(url, Iterable.class);
        model.addAttribute("products", products);
        return "search";
    }

    @GetMapping("/auth")
    public String goAuth(Model model, HttpServletRequest request, RedirectAttributes ra) {
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
        if (session.getAttribute("userLogin") != null) {
            ra.addFlashAttribute("message", "Login success!");
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/auth/login")
    public String goLogin(User user, RedirectAttributes ra, HttpServletRequest request, HttpServletResponse response) {
        String api = "/api/auth/login/user-valid";
        String url = baseUrl + api;
        String requestJson = "{\"username\":\"" + user.getUsername() + "\",\"password\":\"" + user.getPassword() + "\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        String strUser = restTemplate.postForObject(url, entity, String.class);
        Gson gson = new Gson();
        User userValid = gson.fromJson(strUser, User.class);
        if (userValid != null) {
            HttpSession session = request.getSession();
            Cookie ck = new Cookie("username", user.getUsername());
            Cookie ck1 = new Cookie("password", user.getPassword());
            response.addCookie(ck);
            response.addCookie(ck1);
            session.setAttribute("userLogin", userValid);
        } else ra.addFlashAttribute("message", "Password or Username invalid!");
        return "redirect:/auth";
    }

    @GetMapping("/auth/login")
    public String goLogin() {
        return "redirect:/auth";
    }

    @GetMapping("/auth/signup")
    public String goSignUp() {
        return "redirect:/auth";
    }

    @PostMapping("/auth/signup")
    public String goSignUp(User user, RedirectAttributes ra) {
        String api = "/api/user/add";
        String url = baseUrl + api;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Gson gson = new Gson();
        String requestJson = gson.toJson(user);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        String strMessage = restTemplate.postForObject(url, entity, String.class);
        ra.addFlashAttribute("messageRegister", strMessage);
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
