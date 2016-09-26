package controllers.entity;

import entities.user.User;
import entities.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ANykytenko on 8/2/2016.
 */

@Controller
@RequestMapping("/REST")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/User", method = RequestMethod.GET) //GET
    @ResponseBody
    public List<User> getUsers() throws SQLException {
        return userDao.get();
    }

    @RequestMapping(value = "/User/{id}", method = RequestMethod.POST) //POST
    public ModelAndView getUser(@PathVariable("id") int id) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", "User");
        model.put("object", userDao.get(id));
        ModelAndView modelAndView = new ModelAndView("restResult", model);
        return modelAndView;
    }

    @RequestMapping(value = "/User/{firstName}/{lastName}", method = RequestMethod.PUT) //PUT
    public ModelAndView createUser(@PathVariable("firstName") String firstName,
                                             @PathVariable("lastName") String lastName) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        if (userDao.contains(firstName, lastName)) {
            model.put("message", "Base contains user");
        } else {
            model.put("message", "Created user");
            userDao.create(firstName, lastName);
        }
        model.put("object", firstName + " " + lastName);
        ModelAndView modelAndView = new ModelAndView("restResult", model);
        return modelAndView;
    }

    @RequestMapping(value = "/User/{id}/{firstName}/{lastName}", method = RequestMethod.PUT) //PUT
    public ModelAndView updateUser(@PathVariable("id") int id,
                                             @PathVariable("firstName") String firstName,
                                             @PathVariable("lastName") String lastName) throws SQLException {
        userDao.update(id, firstName, lastName);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", "Updated user");
        model.put("object", userDao.get(id));
        ModelAndView modelAndView = new ModelAndView("restResult", model);
        return modelAndView;
    }

    @RequestMapping(value = "/User/{id}", method = RequestMethod.DELETE) //DELETE
    public ModelAndView deleteUser(@PathVariable("id") int id) throws SQLException {
        userDao.delete(id);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", "Deleted user");
        model.put("object", id);
        ModelAndView modelAndView = new ModelAndView("restResult", model);
        return modelAndView;
    }
}
