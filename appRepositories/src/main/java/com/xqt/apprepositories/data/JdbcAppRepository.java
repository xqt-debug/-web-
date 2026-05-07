package com.xqt.apprepositories.data;

import com.xqt.apprepositories.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:application.yaml")
public class JdbcAppRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.repolocation.url}")
    private String basePath;

    @Autowired
    public JdbcAppRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Iterable<App> findAll() {
        return jdbcTemplate.query(
                "select id, name, description from App",
                (rs, rowNum) -> {
                    return new App(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            "/download/"+rs.getString("name")
                    );
                }
        );
    }

    public Iterable<App> findByName(String name) {
        String searchName = "%"+name+"%";
        return jdbcTemplate.query(
                "select id, name, description from App where name ILIKE ? " +
                        "OR SOUNDEX(name) = SOUNDEX(?)" +
                        "OR DIFFERENCE(name, ?) >= 2",
                (rs, rowNum) -> {
                    return new App(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            "/download/"+rs.getString("name")
                    );
                },
                searchName, searchName, searchName
        );
    }
}
