package ngSpring.demo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ngSpring.demo.domain.dto.UserDTO;
import ngSpring.demo.domain.entities.User;
import ngSpring.demo.transformer.impl.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.security.Principal;

@Api(basePath = "/api/login", value = "Login", description = "Login operations", produces = "application/json")
@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

    @Autowired
    UserTransformer userTransformer;

    @Autowired
    UserDetailsService userDetailsService;

    @ApiOperation(value = "Get user details", notes = "Get existing user dataset")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "Login errors")})
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserDTO getUserDetails() throws LoginException {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null) {
            User user = (User) userDetailsService.loadUserByUsername(principal.getName());
            UserDTO userDTO = userTransformer.transformToDTO(user);
            return userDTO;
        } else {
            throw new LoginException();
        }
    }
}
