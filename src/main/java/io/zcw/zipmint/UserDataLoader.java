/**
 * Implementing this User Class Loader causes an unknown conflict with the default
 * JHipster Admin Settings.
 */

package io.zcw.zipmint;

import io.zcw.zipmint.domain.User;
import io.zcw.zipmint.repository.UserRepository;
import io.zcw.zipmint.service.MailService;
import io.zcw.zipmint.service.UserService;
import io.zcw.zipmint.service.mapper.UserMapper;
import io.zcw.zipmint.web.rest.UserResource;
import io.zcw.zipmint.web.rest.errors.ExceptionTranslator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
public class UserDataLoader {

    private static final String DEFAULT_LOGIN = "batman";
    // private static final String UPDATED_LOGIN = "batman";

    private static final Long DEFAULT_ID = 1L;

    private static final String DEFAULT_PASSWORD = "Batman1!";
    // private static final String UPDATED_PASSWORD = "passjhipster";

    private static final String DEFAULT_EMAIL = "batman@localhost";
    // private static final String UPDATED_EMAIL = "jhipster@localhost";

    private static final String DEFAULT_FIRSTNAME = "Bruce";
    // private static final String UPDATED_FIRSTNAME = "jhipsterFirstName";

    private static final String DEFAULT_LASTNAME = "Wayne";
    // private static final String UPDATED_LASTNAME = "jhipsterLastName";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
   //  private static final String UPDATED_IMAGEURL = "http://placehold.it/40x40";

    private static final String DEFAULT_LANGKEY = "en";
    // private static final String UPDATED_LANGKEY = "fr";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserResource userResource;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private CacheManager cacheManager;

    private User user;

    @Autowired
    public UserDataLoader(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Create a User.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the User entity.
     */
    public static User createEntity(EntityManager em) {
        User user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        return user;
    }

    @PostConstruct
    public void loadUser() {
        user = createEntity(em);
        user.setLogin(DEFAULT_LOGIN);
        user.setEmail(DEFAULT_EMAIL);
        userRepository.save(user);
    }

}
