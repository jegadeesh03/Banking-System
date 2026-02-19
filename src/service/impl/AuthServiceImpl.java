package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.AuthService;
import util.HashUtil;

public class AuthServiceImpl implements AuthService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) throws Exception {
        User u = userDao.findByUsername(username);
        if (u == null) return null;
        if (u.getPasswordHash().equals(HashUtil.sha256(password)))
            return u;
        return null;
    }
}
