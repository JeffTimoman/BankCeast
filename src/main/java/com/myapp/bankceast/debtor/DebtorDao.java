package com.myapp.bankceast.debtor;

import com.myapp.bankceast.models.Debtor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class DebtorDao {

    public final JdbcTemplate jdbcTemplate;

    public DebtorDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Debtor> findAll() {
        List<Debtor> results = jdbcTemplate.query("SELECT id, name, email, nip FROM debtors", new DebtorRowMapper());
        return results.stream().toList();
    }

    public Optional<Debtor> findById(Long id) {
        List<Debtor> results = jdbcTemplate.query("SELECT id, name, email, nip FROM debtors WHERE id = ? LIMIT 1", new DebtorRowMapper(), id);
        return results.stream().findFirst();
    }

    public void save(Debtor debtor) {
        jdbcTemplate.update("UPDATE debtors SET name = ?, email= ?, nip = ? WHERE id = ?", debtor.getName(), debtor.getEmail(), debtor.getNip(), debtor.getId());
    }

    public Debtor create(String name, String email, String nip) {
        String insertSql = "INSERT INTO debtors (name, email, nip) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, name); ps.setString(2, email);
            ps.setString(3, nip);
            return ps;
        }, keyHolder);
        Optional <Debtor> debtor = this.findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return debtor.orElse(null);
    }

    public Debtor findByNip(String nip){
        try {
            final Debtor debtor = jdbcTemplate.queryForObject("SELECT * FROM debtors WHERE nip = ? LIMIT 1", new DebtorRowMapper(), nip);
            return debtor;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Debtor findByEmail(String email){
        try {
            final Debtor debtor = jdbcTemplate.queryForObject("SELECT * FROM debtors WHERE email = ? LIMIT 1", new DebtorRowMapper(), email);
            return debtor;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public static class DebtorRowMapper implements RowMapper<Debtor> {
        @Override
        public Debtor mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Debtor.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .nip(rs.getString("nip"))
                    .build();
        }
    }

}
