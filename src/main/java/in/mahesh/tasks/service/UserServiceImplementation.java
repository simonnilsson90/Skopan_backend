   package in.mahesh.tasks.service;

import in.mahesh.tasks.repository.UserRepository;
import in.mahesh.tasks.usermodel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Implement UserService methods
    @Override
    public List<User> getAllUser() {
        // Din logik här
        return null;
    }

    @Override
    public User findUserProfileByJwt(String jwt) {
        // Din logik här
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        // Använd repository för att hitta användaren
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(String userId) {
        // Din logik här
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        // Din logik här
        return null;
    }

    // Override UserDetailsService method
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with this email: " + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
}
