package ngSpring.demo.transformer.impl;


import ngSpring.demo.domain.dto.UserDTO;
import ngSpring.demo.domain.entities.User;
import ngSpring.demo.exceptions.EntityNotFoundException;
import ngSpring.demo.repositories.UserRepository;
import ngSpring.demo.transformer.GenericTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserTransformer extends GenericTransformer<User, UserDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO transformToDTO(User entity) {
        if (entity != null) {
            List<String> permissions = new ArrayList<>();
            return UserDTO.builder()
                    .username(entity.getUsername())
                    .deleted(entity.isDeleted())
                    .build();
        }
        return null;
    }

    @Override
    public User transformToEntity(UserDTO dto) throws EntityNotFoundException {
        if (dto != null) {
            User user = userRepository.findByUsernameAndDeletedFalse(dto.getUsername());
            if (user == null) {
                throw new EntityNotFoundException(dto.getUsername());
            } else {
                return user;
            }
        }
        return null;
    }
}
