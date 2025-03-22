package se.lexicon.model.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.DAO.impl.AppUserDAOCollection;
import se.lexicon.model.*;

import java.time.LocalDate;
import java.util.Optional;

public class TestAppUserDAOCollection {
    private AppUser appUser;
    private AppUserDAOCollection testObject;


    @BeforeEach
    public void setup(){
        appUser = new AppUser("user", "password", AppRole.ROLE_APP_USER);
        testObject = new AppUserDAOCollection();
    }

    @Test
    public void test_add_AppUser(){
        Assertions.assertEquals(testObject.persist(appUser), Optional.of(appUser));
    }

    @Test
    public void test_findUserByUsername_found(){
        testObject.persist(appUser);
        Assertions.assertEquals(testObject.findByUsername("user"), Optional.of(appUser));
        Assertions.assertNotNull(testObject.findByUsername("user"));
    }
    @Test
    public void test_findUserByUsername_notFound(){
        testObject.persist(appUser);
        Assertions.assertEquals(testObject.findByUsername("user2"), Optional.empty());
    }

    @Test
    public void test_removeUser(){
        testObject.persist(appUser);
        testObject.remove("user");
        Assertions.assertFalse(testObject.findAll().size() > 1);
    }

}
