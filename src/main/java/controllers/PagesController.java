package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import support.holders.SessionsHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ANykytenko on 8/16/2016.
 */
@Controller
public class PagesController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SessionsHolder sessionsHolder;

    @RequestMapping(value = {"/Pages/Challenges"}, method = {RequestMethod.GET})
    public ModelAndView challengesPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User securityUser = (User) (auth.getPrincipal());
        ModelAndView model = new ModelAndView();
        model.addObject("user", sessionsHolder.getUserByName(securityUser.getUsername()));
        model.setViewName("Challenges");
        return model;
    }

    @RequestMapping(value = {"/", "/Pages", "/Pages/Ranks"}, method = {RequestMethod.GET})
    public ModelAndView rankPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User securityUser = (User) (auth.getPrincipal());
        ModelAndView model = new ModelAndView();
        model.addObject("user", sessionsHolder.getUserByName(securityUser.getUsername()));
        model.setViewName("Ranks");
        return model;
    }

    /**
     * method allows to log out current user
     * @param request is http request to servlet
     * @param response is http response from servlet
     * @return redirect string to logout page
     * @throws ServletException
     */
    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public String logout(final HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            for (SessionInformation sessionInformation : sessionsHolder.getActiveSessions(auth.getPrincipal())) {
                sessionInformation.expireNow();
            }
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        notifyClients();
        return "redirect:/";
    }

    private void notifyClients() {
        messagingTemplate.convertAndSend("/UpdateEvent/All", "{'status' : 'ok'}");
    }
}
