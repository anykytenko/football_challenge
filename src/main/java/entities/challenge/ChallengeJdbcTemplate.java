package entities.challenge;

import entities.AbstractJdbcTemplate;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public class ChallengeJdbcTemplate extends AbstractJdbcTemplate implements ChallengeDao {

    public ChallengeJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public void create(int hostUserId, int receivingUserId) {
        String sql = "INSERT INTO " + TABLE_NAME +" (" + HOST_USER_ID_FIELD_NAME + ", " + RECEIVING_USER_ID_FIELD_NAME +
                ", " + DATE_OF_CREATION_FIELD_NAME + ") VALUES (?, ?, ?)";
        Object[] objects = {hostUserId, receivingUserId, new Date(System.currentTimeMillis())};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }

    public void create(int hostUserId, int otherUser1Id, int guestUserId, int otherUser2Id, int team1Goals, int team2Goals) {
        String sql = "INSERT INTO " + TABLE_NAME +" (" + HOST_USER_ID_FIELD_NAME + ", " + OTHER_USER1_ID_FIELD_NAME +
                ", " + RECEIVING_USER_ID_FIELD_NAME + ", " + OTHER_USER2_ID_FIELD_NAME + ", " + TEAM1_GOALS_FIELD_NAME +
                ", " + TEAM2_GOALS_FIELD_NAME + "," + STATUS_ID_FIELD_NAME + ", " + DATE_OF_CLOSING_FIELD_NAME +
                ", " + DATE_OF_CREATION_FIELD_NAME + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] objects = {hostUserId, otherUser1Id, guestUserId, otherUser2Id, team1Goals, team2Goals,
                            Challenge.Status.CLOSED.getId(), new Date(System.currentTimeMillis()),
                            new Date(System.currentTimeMillis())};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }

    public List<Challenge> getActive(int year, int month) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + STATUS_ID_FIELD_NAME + " NOT IN(?) AND MONTH(" +
                        DATE_OF_CREATION_FIELD_NAME + ") = MONTH(?) ORDER BY " + DATE_OF_CREATION_FIELD_NAME;
        Date date = null;
        try {
            date = formatter.parse("01-" + month + "-" + year);
        } catch (Exception ex) {
            System.out.println("Error parsing date");
        }
        Object[] objects = {Challenge.Status.CLOSED.getId(), date};
        List<Challenge> challenges = jdbcTemplateObject.query(sql, objects, new ChallengeMapper());
        releaseConnection();
        return challenges;
    }

    public List<Challenge> getClosed(int year, int month) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + STATUS_ID_FIELD_NAME + " IN(?) AND MONTH(" +
                        DATE_OF_CLOSING_FIELD_NAME + ") = MONTH(?) ORDER BY " + DATE_OF_CLOSING_FIELD_NAME;
        Date date = null;
        try {
            date = formatter.parse("01-" + month + "-" + year);
        } catch (Exception ex) {
            System.out.println("Error parsing date");
        }
        Object[] objects = {Challenge.Status.CLOSED.getId(), date};
        List<Challenge> challenges = jdbcTemplateObject.query(sql, objects, new ChallengeMapper());
        releaseConnection();
        return challenges;
    }

    public void approve(Integer id) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS_ID_FIELD_NAME + " = ? WHERE " + ID_FIELD_NAME + " = ?";
        Object[] objects = {Challenge.Status.APPROVED.getId(), id};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }

    public void reject(Integer id) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS_ID_FIELD_NAME + " = ? WHERE " + ID_FIELD_NAME + " = ?";
        Object[] objects = {Challenge.Status.REJECTED.getId(), id};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }

    public void close(Integer id, int team1Goals, int team2Goals) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS_ID_FIELD_NAME + " = ?, " + TEAM1_GOALS_FIELD_NAME + " = ?," +
                TEAM2_GOALS_FIELD_NAME + "= ?, " + DATE_OF_CLOSING_FIELD_NAME + " = ? WHERE " + ID_FIELD_NAME + " = ?";
        Object[] objects = {Challenge.Status.CLOSED.getId(), team1Goals, team2Goals, new Date(System.currentTimeMillis()), id};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }

    public void assign(int challengeId, int otherUserNumber/*1 or 2*/, int userId) {
        String sql = "UPDATE " + TABLE_NAME + " SET OTHER_USER" + otherUserNumber + "_ID = ? WHERE " + ID_FIELD_NAME + " = ?";
        Object[] objects = {userId, challengeId};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }
}
