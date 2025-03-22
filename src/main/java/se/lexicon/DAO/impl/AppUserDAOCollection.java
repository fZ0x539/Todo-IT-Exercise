package se.lexicon.DAO.impl;

import se.lexicon.model.AppUser;
import se.lexicon.DAO.iAppUserDAO;
import se.lexicon.view.ConsoleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AppUserDAOCollection implements iAppUserDAO {

    private final List<AppUser> storage = new ArrayList<>();

    @Override
    public Optional<AppUser> persist(AppUser appUser) {
        Objects.requireNonNull(appUser, "AppUser can't be null");
        if(!storage.contains(appUser)){
            storage.add(appUser);
            return Optional.of(appUser);
        } else
            ConsoleUI.printErrorMessage("This username is already in use");

        return Optional.empty();
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        for(AppUser user : storage){
            if(user.getUsername().equals(username))
                return Optional.of(user);
        }
        ConsoleUI.printErrorMessage("User: " + username + " not found.");
        return Optional.empty();
    }

    @Override
    public List<AppUser> findAll() {
        return storage;
    }

    @Override
    public void remove(String username) {
        for(AppUser user : storage){
            if(user.getUsername().equals(username)){
                storage.remove(user);
                ConsoleUI.printSuccessMessage(user.getUsername() + " successfully removed from storage");
                break;
            }
        }
    }
}
