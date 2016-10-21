package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import support.holders.ChallengeHolder;
import support.holders.SessionsHolder;

/**
 * Created by ANykytenko on 10/21/2016.
 */
@Controller
@RequestMapping("/Support/")
public class SupportController {

    @Autowired
    private ChallengeHolder challengeHolder;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SessionsHolder sessionsHolder;

    @RequestMapping(value = "/Ui/Update", method = {RequestMethod.GET})
    @ResponseBody
    public String challengesUpdate() {
        sessionsHolder.setNeedUpdate(true);
        challengeHolder.setNeedUpdateActive(true);
        challengeHolder.setNeedUpdateClosed(true);
        notifyClients();
        return "ok";
    }

    private void notifyClients() {
        messagingTemplate.convertAndSend("/UpdateEvent/All", "{'status' : 'ok'}");
    }
}
