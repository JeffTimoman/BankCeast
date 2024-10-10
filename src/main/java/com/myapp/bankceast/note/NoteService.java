package com.myapp.bankceast.note;

import com.myapp.bankceast.debtor.DebtorDao;
import com.myapp.bankceast.exception.ApiNotFoundException;
import com.myapp.bankceast.models.Debtor;
import com.myapp.bankceast.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteDao noteDao;
    private final DebtorDao debtorDao;

    public NoteService(NoteDao noteDao, DebtorDao debtorDao) {
        this.noteDao = noteDao;
        this.debtorDao = debtorDao;
    }

    public List findAll(){
        final List all = noteDao.findAll();
        return all;
    }

    public Optional<Note> findById(Long id) {
        final Optional<Note> result = noteDao.findById(id);

        if (result.isEmpty()){
            throw new ApiNotFoundException("There is no note with id " + id + " .");
        }

        return result;
    }

    public Note create(Integer amount, Long debtorId) {
        Optional <Debtor> debtor = debtorDao.findById(debtorId);
        if(debtor.isEmpty()){
            throw new ApiNotFoundException("There is no debtor with id " + debtorId + " .");
        }

        Note note = noteDao.create(amount, debtorId);
        if (note == null){
            throw new ApiNotFoundException("There is no note with id " + debtorId + " .");
        }

        return note;
    }
}
