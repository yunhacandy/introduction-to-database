package hongik.loginProject.controller;

import hongik.loginProject.entity.User;
import hongik.loginProject.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        Model model) {
        User user = userRepository.findByUsername(username);
        if (Objects.equals(user.getPassword(), password)) {
            model.addAttribute("username", username);
            return "loginSuccess";
        } else {
            return "redirect:login";
        }
    }
}
