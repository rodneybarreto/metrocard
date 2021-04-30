package br.uece.metrocard.controller;

import br.uece.metrocard.domain.dto.UserDto;
import br.uece.metrocard.domain.entity.User;
import br.uece.metrocard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    private UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/auth")
    public String auth(UserDto userDto, Model model) {
        Optional<User> optional = userRepository.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
        if (optional.isPresent()) {
            model.addAttribute("user", optional.get());
            return "welcome";
        }
        return "login";
    }

}
