package in.mahesh.tasks.service;

import in.mahesh.tasks.usermodel.User;

import java.util.List;


public interface UserService {


    public List<User> getAllUser()  ;

    public User findUserProfileByJwt(String jwt);

    public User findUserByEmail(String email) ;

    public User findUserById(String userId) ;

    public List<User> findAllUsers();


}
