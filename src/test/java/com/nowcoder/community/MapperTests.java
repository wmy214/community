package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectDiscussPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,10);
        for(DiscussPost post : list) {
            System.out.println(post);
        }
    }

    @Test
    public void testSelectDiscussPostRows(){
        int count = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(count);
    }

//    @Test
//    public void testSelectById(){
//        User user = userMapper.selectById(101);
//        System.out.println(user);
//    }
    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStstus("abc", 1);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }

    @Test
    public void testSelectLetters(){
        List<Message> list = messageMapper.selectConversations(111,0,20);
        for (Message m : list) {
            System.out.println(m);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message m : list) {
            System.out.println(m);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131,"111_131");
        System.out.println(count);


    }

}
