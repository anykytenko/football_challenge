package controllers.entity;

import entities.message.Message;
import entities.message.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ANykytenko on 8/26/2016.
 */

@Controller
@RequestMapping(value = "/Message")
public class MessageController {

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> lastMessages() {
        return messageDao.getLast(50);
    }

    @MessageMapping("/Message/Create")
    @SendTo("/UpdateEvent/Chat")
    public boolean sendMessage(Message message) {
        if (message.getText().equals("")) {
            return true;
        }
        String formattedText = message.getText().replaceAll("(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)",
                "$1<a href=\"$2\">$2</a>$4");
        message.setText(formattedText);
        messageDao.create(message.getUserId(), message.getText());
        return true;
    }
}
