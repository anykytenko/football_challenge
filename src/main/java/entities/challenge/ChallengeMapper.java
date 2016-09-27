package entities.challenge;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public class ChallengeMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Challenge challenge = new Challenge();
        challenge.setId(rs.getInt(ChallengeDao.ID_FIELD_NAME));
        challenge.setHostUserId(rs.getInt(ChallengeDao.HOST_USER_ID_FIELD_NAME));
        challenge.setReceivingUserId(rs.getInt(ChallengeDao.RECEIVING_USER_ID_FIELD_NAME));
        challenge.setOtherUser1Id(rs.getInt(ChallengeDao.OTHER_USER1_ID_FIELD_NAME));
        challenge.setOtherUser2Id(rs.getInt(ChallengeDao.OTHER_USER2_ID_FIELD_NAME));
        challenge.setStatus(Challenge.Status.getById(rs.getInt(ChallengeDao.STATUS_ID_FIELD_NAME)));
        challenge.setTeam1Goals(rs.getInt(ChallengeDao.TEAM1_GOALS_FIELD_NAME));
        challenge.setTeam2Goals(rs.getInt(ChallengeDao.TEAM2_GOALS_FIELD_NAME));
        challenge.setDateOfClosing(rs.getDate(ChallengeDao.DATE_OF_CLOSING_FIELD_NAME));
        challenge.setDateOfCreation(rs.getDate(ChallengeDao.DATE_OF_CREATION_FIELD_NAME));
        return challenge;
    }
}
