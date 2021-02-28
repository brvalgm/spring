package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Logger logger = Logger.getLogger(UserService.class);

    private final ProjectRepository<User> userRepo;

    @Autowired
    public UserService(@Qualifier("userRepository") ProjectRepository<User> userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.retrieveAll();
    }

    public boolean saveUser(User user) {
        if (user.getName() != "" && user.getPassword() != "") {
            userRepo.store(user);
            return true;
        } else {
            return false;
        }
    }

    public void removeUserById(Integer userIdToRemove) {
        userRepo.removeItemById(userIdToRemove);
    }

    public boolean authenticate(User checkUser) {
        for (User user : userRepo.retrieveAll()) {
            if (user.getName().equals(checkUser.getName()) &&  user.getPassword().equals(checkUser.getPassword())) {
                logger.info("try auth with user-form: " + checkUser);
                return true;
            }
        }
        return false;
    }

}
