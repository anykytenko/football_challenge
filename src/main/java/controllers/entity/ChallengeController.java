package controllers.entity;

import entities.challenge.Challenge;
import entities.challenge.ChallengeDao;
import entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import support.holders.ChallengeHolder;
import support.holders.SessionsHolder;
import support.mail.MailTransport;
import support.strings.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ANykytenko on 8/12/2016.
 */

@Controller
@RequestMapping("/Challenge")
public class ChallengeController {

    private Random random = new Random();

    @Autowired
    private ChallengeDao challengeDao;

    @Autowired
    private ChallengeHolder challengeHolder;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MailTransport mailTransport;

    @Autowired
    private SessionsHolder sessionsHolder;

    @Autowired
    private Strings strings;

    @RequestMapping(value = "/Create/{hostUserId}/{receivingUserId}", method = RequestMethod.GET)
    public String createChallenge(@PathVariable("hostUserId") int hostUserId,
                                  @PathVariable("receivingUserId") int receivingUserId) {
        challengeDao.create(hostUserId, receivingUserId);
        challengeHolder.setNeedUpdateActive(true);
        notifyClient(receivingUserId);
        notifyClient(receivingUserId);
        User hostUser = sessionsHolder.getUserById(hostUserId);
        User receivingUser = sessionsHolder.getUserById(receivingUserId);
        mailTransport.sendMessage(strings.challengeCreatedSubject(hostUser), strings.challengeCreatedMessage(hostUser),
                                    receivingUser);
        return "redirect:/";
    }

    @RequestMapping(
            value = "/Create/{hostUserId}/{otherUser1Id}/{receivingUserId}/{otherUser2Id}/{team1Goals}/{team2Goals}",
            method = RequestMethod.GET)
    public String createChallenge(@PathVariable("hostUserId") int hostUserId,
                                  @PathVariable("otherUser1Id") int otherUser1Id,
                                  @PathVariable("receivingUserId") int receivingUserId,
                                  @PathVariable("otherUser2Id") int otherUser2Id,
                                  @PathVariable("team1Goals") int team1Goals,
                                  @PathVariable("team2Goals") int team2Goals) {
        challengeDao.create(hostUserId, otherUser1Id, receivingUserId, otherUser2Id, team1Goals, team2Goals);
        challengeHolder.setNeedUpdateActive(true);
        notifyClients();
        return "redirect:/";
    }

    @RequestMapping(value = "/Approve/{hostUserId}/{receivingUserId}", method = RequestMethod.GET)
    public String approveChallenge(@PathVariable("hostUserId") int hostUserId,
                                   @PathVariable("receivingUserId") int receivingUserId) {
        Challenge challenge = challengeHolder.ONE.PERSONAL_BETWEEN.getActiveWhereHostAndReceiving(hostUserId, receivingUserId);
        challengeDao.approve(challenge.getId());
        challengeHolder.setNeedUpdateActive(true);
        notifyClient(hostUserId);
        notifyClient(receivingUserId);
        notifyClients();
        User hostUser = sessionsHolder.getUserById(challenge.getHostUserId());
        User receivingUser = sessionsHolder.getUserById(challenge.getReceivingUserId());
        mailTransport.sendMessage(strings.challengeApprovedSubject(receivingUser),
                                    strings.challengeApprovedMessage(receivingUser), hostUser);
        return "redirect:/";
    }

    @RequestMapping(value = "/Reject/{hostUserId}/{receivingUserId}", method = RequestMethod.GET)
    public String rejectChallenge(@PathVariable("hostUserId") int hostUserId,
                                  @PathVariable("receivingUserId") int receivingUserId) {
        Challenge challenge = challengeHolder.ONE.PERSONAL_BETWEEN.getActiveWhereHostAndReceiving(hostUserId, receivingUserId);
        challengeDao.reject(challenge.getId());
        challengeHolder.setNeedUpdateActive(true);
        notifyClient(hostUserId);
        notifyClient(receivingUserId);
        User hostUser = sessionsHolder.getUserById(challenge.getHostUserId());
        User receivingUser = sessionsHolder.getUserById(challenge.getReceivingUserId());
        mailTransport.sendMessage(strings.challengeRejectedSubject(receivingUser),
                                    strings.challengeRejectedMessage(receivingUser), hostUser);
        return "redirect:/";
    }

    @RequestMapping(value = "/Close/{id}/{team1Goals}/{team2Goals}", method = RequestMethod.GET)
    public String closeChallenge(@PathVariable("id") int id,
                                 @PathVariable("team1Goals") int team1Goals,
                                 @PathVariable("team2Goals") int team2Goals) {
        challengeDao.close(id, team1Goals, team2Goals);
        challengeHolder.setNeedUpdateActive(true);
        challengeHolder.setNeedUpdateClosed(true);
        notifyClients();
        return "redirect:/";
    }

    @RequestMapping(value = "/Assign/{challengeId}/{userId}", method = RequestMethod.GET)
    public String assignToChallenge(@PathVariable("challengeId") int challengeId,
                                    @PathVariable("userId") int userId) {
        Challenge challenge = challengeHolder.ONE.CONCRETE.get(challengeId);
        List<Integer> list = new ArrayList<Integer>();
        if (challenge.getOtherUser1Id() == -1) {
            list.add(1);
        }
        if (challenge.getOtherUser2Id() == -1) {
            list.add(2);
        }
        if (!list.isEmpty()) {
            challengeDao.assign(challengeId, list.get(random.nextInt(list.size())), userId);
            challengeHolder.setNeedUpdateActive(true);
            notifyClients();
            challenge = challengeHolder.ONE.CONCRETE.get(challengeId);
            if (challenge.isReady()) {
                User hostUser = sessionsHolder.getUserById(challenge.getHostUserId());
                User receivingUser = sessionsHolder.getUserById(challenge.getReceivingUserId());
                User otherUser1 = sessionsHolder.getUserById(challenge.getOtherUser1Id());
                User otherUser2 = sessionsHolder.getUserById(challenge.getOtherUser2Id());
                User penultimate = otherUser1.getId().equals(userId) ? otherUser2 : otherUser1;
                mailTransport.sendMessage(strings.challengeReadyToStartSubject(),
                                strings.challengeReadyToStartMessage(hostUser, otherUser1, receivingUser, otherUser2),
                                hostUser, receivingUser, penultimate);
            }
        }
        return "redirect:/";
    }

    private void notifyClients() {
        messagingTemplate.convertAndSend("/UpdateEvent/All", "{'status' : 'ok'}");
    }

    private void notifyClient(int userId) {
        messagingTemplate.convertAndSend("/UpdateEvent/" + userId, "{'status' : 'ok'}");
    }
}
