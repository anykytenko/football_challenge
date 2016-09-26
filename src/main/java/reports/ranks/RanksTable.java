package reports.ranks;

import entities.user.User;

import java.util.List;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public class RanksTable {

    private User user;
    private List<Row> rows;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
