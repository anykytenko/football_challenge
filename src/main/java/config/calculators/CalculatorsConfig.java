package config.calculators;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import support.calculators.challenge_list.ChallengesListCalculator;
import support.calculators.challenge_list.CustomChallengesListCalculator;
import support.calculators.messages_list.CustomMessagesListCalculator;
import support.calculators.messages_list.MessagesListCalculator;
import support.calculators.ranks_table.CustomTableCalculator;
import support.calculators.ranks_table.TableCalculator;
import support.calculators.ranks_table.point.CustomResultsCalculator;
import support.calculators.ranks_table.point.ResultsCalculator;

/**
 * Created by ANykytenko on 8/18/2016.
 */
@Configuration
public class CalculatorsConfig {

    @Bean
    @Scope("prototype")
    public TableCalculator tableCalculator() {
        return new CustomTableCalculator();
    }

    @Bean
    @Scope("prototype")
    public ResultsCalculator pointsCalculator() {
        return new CustomResultsCalculator();
    }

    @Bean
    @Scope("prototype")
    public ChallengesListCalculator challengesListCalculator() {
        return new CustomChallengesListCalculator();
    }

    @Bean
    @Scope("prototype")
    public MessagesListCalculator messagesListCalculator() {
        return new CustomMessagesListCalculator();
    }
}
