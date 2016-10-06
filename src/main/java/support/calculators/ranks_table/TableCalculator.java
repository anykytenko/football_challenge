package support.calculators.ranks_table;

import org.springframework.security.core.userdetails.User;
import reports.ranks.RanksTable;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public interface TableCalculator {
    RanksTable calculate(User securityUser, int year, int month);
}
