package org.example.web.controllers;
import org.apache.log4j.Logger;
import org.example.app.services.UserService;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private final Logger logger = Logger.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String login(Model model) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("user", new User());
        return "login_page";
    }

    @PostMapping(value = "/auth")
    public String authenticate(User user) {
        if (userService.authenticate(user)) {
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login FAIL redirect to login");
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        logger.info("GET /registration returns registration_page.html");
        model.addAttribute("user", new User());
        return "registration_page";
    }

    @PostMapping(value = "/registration")
    public String saveUser(User user) {
        if (userService.saveUser(user)) {
            logger.info("registration OK redirect to login");
            return "redirect:/login";
        } else {
            logger.info("registration FAIL redirect to registration");
            return "redirect:/registration";
        }
    }
}
