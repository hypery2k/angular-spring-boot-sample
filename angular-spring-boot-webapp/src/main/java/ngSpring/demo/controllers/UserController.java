package ngSpring.demo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ngSpring.demo.domain.dto.UserProfileDTO;
import ngSpring.demo.domain.entities.User;
import ngSpring.demo.errorhandling.ValidationMessage;
import ngSpring.demo.exceptions.EntityNotFoundException;
import ngSpring.demo.exceptions.ValidationException;
import ngSpring.demo.repositories.UserRepository;
import ngSpring.demo.transformer.impl.UserProfileTransformer;
import ngSpring.demo.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Api(basePath = "/api/user", value = "User", description = "User operations", produces = "application/json")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileTransformer userProfileTransformer;


    @ApiOperation(value = "Update user details", notes = "Update existing user dataset")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Validation errors")})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> postUser(@RequestBody UserProfileDTO userProfileDTO) throws ValidationException {

        User loggedInUser = checkUser();
        validateUserProfileDto(userProfileDTO);
        User newUser = this.userProfileTransformer.transformToEntity(userProfileDTO);

        newUser.setEnabled(true);
        this.userRepository.save(newUser);
        return new ResponseEntity<Message>(new Message("The user has been properly created."), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Message> putUser(@PathVariable String userId, @RequestBody UserProfileDTO userProfileDTO) throws ValidationException, EntityNotFoundException {

        User loggedInUser = checkUser();
        validateUserProfileDto(userProfileDTO);
        User currentUser = this.userRepository.findByUserIdAndDeletedFalse(userId);
        if (currentUser == null) {
            throw new EntityNotFoundException();
        }
        User updatedUser = this.userProfileTransformer.transformToEntity(userProfileDTO);
        updatedUser.setEnabled(currentUser.isEnabled());
        updatedUser.setInsertDate(currentUser.getInsertDate());
        this.userRepository.save(updatedUser);
        return new ResponseEntity<Message>(new Message("The user has been properly updated."), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteUser(@PathVariable String userId) throws EntityNotFoundException {
        User loggedInUser = checkUser();
        User toBeDeleted = this.userRepository.findByUserIdAndDeletedFalse(userId);

        if (toBeDeleted == null) {
            throw new EntityNotFoundException();
        }

        this.userRepository.save(toBeDeleted);
        return new ResponseEntity<Message>(new Message("The user has been properly deleted."), HttpStatus.OK);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    public ResponseEntity<Message> changePassword(@RequestBody UserProfileDTO dto) throws ValidationException {
        User loggedInUser = checkUser();
        validateUserProfileDto(dto);
        if (loggedInUser.getUserId() == dto.getUserId()) {
            User updatedUser = this.userProfileTransformer.transformToEntity(dto);
            this.userRepository.save(updatedUser);
        } else {
            throw new AccessDeniedException("Not allowed");
        }
        return new ResponseEntity<Message>(new Message("The password has been properly changed."), HttpStatus.OK);
    }

    @RequestMapping(value = "/controller/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<UserProfileDTO> getControllerByCustomerId(@PathVariable String customerId) throws EntityNotFoundException {
        String userName = this.userRepository.findControllerUserNameByCustomerId(customerId);
        User user = this.userRepository.findByUsernameAndDeletedFalse(userName);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        UserProfileDTO userProfileDTO = this.userProfileTransformer.transformToDTO(user);
        return new ResponseEntity<UserProfileDTO>(userProfileDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/planer/{branchId}", method = RequestMethod.GET)
    public ResponseEntity<UserProfileDTO> getPlanerByBranchId(@PathVariable String branchId) throws EntityNotFoundException {
        String userName = this.userRepository.findPlanerUserNameByBranchId(branchId);
        User user = this.userRepository.findByUsernameAndDeletedFalse(userName);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        UserProfileDTO userProfileDTO = this.userProfileTransformer.transformToDTO(user);
        return new ResponseEntity<UserProfileDTO>(userProfileDTO, HttpStatus.OK);
    }

    private User checkUser() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null) {
            return userRepository.findByUsernameAndDeletedFalse(principal.getName());
        } else {
            throw new AccessDeniedException("Not logged in.");
        }
    }


    private void validateUserProfileDto(UserProfileDTO dto) throws ValidationException {
        // check password
        if (dto.getUserId() == null && !StringUtils.pathEquals(dto.getPassword(), dto.getPasswordConfirmation())) {
            List<ValidationMessage> validationMessages = new ArrayList<>();
            validationMessages.add(ValidationMessage.builder()
                    .entity(dto.getUsername())
                    .messageTemplate("validation.passwords_not_match")
                    .build());
            throw new ValidationException("", validationMessages);
        }
    }
}
