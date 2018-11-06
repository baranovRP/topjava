package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;

@Repository
@Profile(Profiles.POSTGRES_DB)
public class JdbcMealRepositoryPostgresImpl extends JdbcMealRepositoryAbstractImpl {

    @Autowired
    public JdbcMealRepositoryPostgresImpl(final JdbcTemplate jdbcTemplate,
                                          final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    LocalDateTime convertDateTime(LocalDateTime dateTime) {
        return dateTime;
    }
}
