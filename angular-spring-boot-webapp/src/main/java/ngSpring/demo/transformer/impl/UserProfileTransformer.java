package ngSpring.demo.transformer.impl;


import ngSpring.demo.domain.dto.UserProfileDTO;
import ngSpring.demo.domain.entities.User;
import ngSpring.demo.repositories.UserRepository;
import ngSpring.demo.transformer.GenericTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserProfileTransformer extends GenericTransformer<User, UserProfileDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User transformToEntity(UserProfileDTO dto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .password(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : this.userRepository.findByUserIdAndDeletedFalse(dto.getUserId()).getPassword())
                .build();
    }

    @Override
    public UserProfileDTO transformToDTO(User user) {
        return UserProfileDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .build();
    }
}
