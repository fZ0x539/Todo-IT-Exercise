package se.lexicon.DAO;

import se.lexicon.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface iAppUserDAO {
     Optional<AppUser> persist(AppUser appUser);
     Optional<AppUser> findByUsername(String username);
     List<AppUser> findAll();
     void remove(String username);

}
