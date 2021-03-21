package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements ProjectRepository<User> {
    private final Logger logger = Logger.getLogger(UserRepository.class);
    private final List<User> repo = new ArrayList<>();

    @Override
    public ArrayList<User> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(User user) {
        logger.info("store new user: " + user);
        repo.add(user);
    }

    @Override
    public void removeItemById(String userIdToRemove) {
        for (User user : retrieveAll()) {
            if (user.getId().equals(userIdToRemove)) {
                logger.info("remove user completed: " + user);
                repo.remove(user);
            }
        }
    }
}
