package com.myapp.bankceast.note;

import com.myapp.bankceast.models.Note;
import lombok.extern.java.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class NoteDao {
    private final JdbcTemplate jdbcTemplate;
    public static Log log;

    public NoteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Object> findAll() {;
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * from notes");

        List<Object> temp = new ArrayList<>();
        while (sqlRowSet.next()) {
            HashMap map = new HashMap();
            map.put("id", sqlRowSet.getLong("id"));
            map.put("amount", sqlRowSet.getString("amount"));
            map.put("title", sqlRowSet.getString("receipt"));
            map.put("debtor_id", sqlRowSet.getLong("debtor_id"));
            map.put("status", sqlRowSet.getInt("status"));
            temp.add(map);
        }
        return temp;
    }

    public Optional<Note> findById(Long id) {
        try {
            Note note = jdbcTemplate.queryForObject("SELECT * FROM notes WHERE id = ? LIMIT 1", new RowMapper<Note>() {
                @Override
                public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return Note.builder()
                            .id(rs.getLong("id"))
                            .amount(rs.getInt("amount"))
                            .receipt(rs.getString("receipt"))
                            .debtorId(rs.getLong("debtor_id"))
                            .status(rs.getInt("status"))
                            .build();
                }
            }, id);
            return Optional.of(note);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Note create(Integer amount, Long debtorId) {
        String insertSql = "INSERT INTO notes (amount, debtor_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSql, new String[]{"id"});
                    ps.setInt(1, amount);
                    ps.setInt(2,
                            Math.toIntExact(debtorId));
                    return ps;
        }, keyHolder);

        Optional <Note> note = this.findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return note.orElse(null);
    }
}
