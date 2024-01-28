package mk.dians.finki.authentication.service.impl;


import mk.dians.finki.authentication.model.User;
import mk.dians.finki.authentication.model.exceptions.UserNameExists;
import mk.dians.finki.authentication.model.exceptions.WrongCredentials;
import mk.dians.finki.authentication.repository.UserRepository;
import mk.dians.finki.authentication.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerNewUser(String username, String email, String password) {

        if (userRepository.findByEmail(email) != null || userRepository.findByUsername(username) != null){
            throw new UserNameExists();
        }

        User user = new User(email, username, password);
        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)){
            throw new WrongCredentials();
        }

        return user;
    }
}
