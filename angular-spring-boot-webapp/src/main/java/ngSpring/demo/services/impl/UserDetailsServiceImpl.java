package ngSpring.demo.services.impl;

import ngSpring.demo.domain.entities.User;
import ngSpring.demo.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = Logger
            .getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndDeletedFalse(username);
        if (user != null) {
            LOG.debug("Found user with name " + username);
            return user;
        } else {
            LOG.error("Cannot find user with name " + username + " in the database.");
            return new User(username, "");
        }
    }

}
