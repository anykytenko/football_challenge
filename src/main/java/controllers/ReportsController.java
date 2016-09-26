package controllers;

import entities.message.MessageDao;
import org.springframework.web.bind.annotation.ResponseBody;
import reports.challenges.ChallengesList;
import reports.challenges.Row;
import reports.messages.MessagesReport;
import reports.ranks.RanksTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import support.holders.SessionsHolder;
import support.calculators.challenge_list.ChallengesListCalculator;
import support.calculators.messages_list.MessagesListCalculator;
import support.calculators.ranks_table.TableCalculator;

/**
 * @author anykytenko
 *
 * Reports controller
 */
@Controller
@RequestMapping("/Reports/")
public class ReportsController {

    @Autowired
    private TableCalculator tableCalculator;

    @Autowired
    private ChallengesListCalculator challengesListCalculator;

    @Autowired
    private MessagesListCalculator messagesListCalculator;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SessionsHolder sessionsHolder;

    @RequestMapping(value = "/RanksTable", method = {RequestMethod.GET})
    @ResponseBody
    public RanksTable ranksTable() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User securityUser = (User) (auth.getPrincipal());
        return tableCalculator.calculate(securityUser);
    }

    @RequestMapping(value = "/ChallengesList", method = {RequestMethod.GET})
    @ResponseBody
    public ChallengesList challengesList() {
        return challengesListCalculator.calculate();
    }

    @RequestMapping(value = "/MessagesList", method = RequestMethod.GET)
    @ResponseBody
    public MessagesReport lastMessages() {
        MessagesReport report = new MessagesReport();
        report.setMessages(messagesListCalculator.calculate(messageDao.getLast(50)));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User securityUser = (User) (auth.getPrincipal());
        report.setUser(sessionsHolder.getUserByName(securityUser.getUsername()));
        return report;
    }
}
