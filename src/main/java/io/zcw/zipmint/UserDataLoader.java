//package io.zcw.zipmint;
//
//import io.zcw.zipmint.domain.User;
//import io.zcw.zipmint.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class UserDataLoader {
//
//    private UserRepository userRepository;
//
//    @Autowired
//    public UserDataLoader(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }
//
//    @PostConstruct
//    private void LoadUsers(){
//        User user1 = new User();
//        user1.setEmail("User1Email@gmail.com");
//        user1.setLogin("user");
//        user1.setPassword("user");
//        user1.setActivated(true);
//        user1.setFirstName("user1FirstName");
//        user1.setLastName("user1LastName");
//        userRepository.save(user1);
//
//        User user2 = new User();
//        user2.setEmail("User2Email@gmail.com");
//        user2.setLogin("user");
//        user2.setPassword("user");
//        user2.setActivated(true);
//        user2.setFirstName("user2FirstName");
//        user2.setLastName("user2LastName");
//        userRepository.save(user2);
//
//
//    }
//
//}
