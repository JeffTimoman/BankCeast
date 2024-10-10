package com.myapp.bankceast.debtor;

import com.myapp.bankceast.exception.ApiBadRequestException;
import com.myapp.bankceast.exception.ApiNotFoundException;
import com.myapp.bankceast.models.Debtor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebtorService {
    public final DebtorDao debtorDao;

    public DebtorService(final DebtorDao debtorDao) {
        this.debtorDao = debtorDao;
    }

    public List<Debtor> findAll() {
        List<Debtor> all = debtorDao.findAll();
        return all;
    }

    public Debtor update(Long id, String name, String email, String nip) {
        Optional <Debtor> debtor = debtorDao.findById(id);
        if(debtor.isEmpty()) throw new ApiNotFoundException("Page not found or data not found.");

        Debtor updated = debtor.get();
        if(name != null && !name.isEmpty()){
            updated.setName(name);
        }

        if(email != null && !email.isEmpty()){
            updated.setEmail(email);
        }

        if(nip != null && !nip.isEmpty()){
            updated.setNip(nip);
        }

        debtorDao.save(updated);

        return updated;
    }

    public Debtor create(String name, String email, String nip) {
        Debtor check;
        if(name == null || name.isEmpty()) throw new ApiBadRequestException("Name is required.");
        if(email == null || email.isEmpty()) throw new ApiBadRequestException("Email is required.");
        check = debtorDao.findByEmail(email);
        if(check != null) throw new ApiBadRequestException("Email has been used.");
        if(nip == null || nip.isEmpty()) throw new ApiNotFoundException("NIP is required");
        check = debtorDao.findByNip(nip);
        if(check != null) throw new ApiBadRequestException("NIP has been used by other debtors.");
        return debtorDao.create(name, email, nip);
    }
}
