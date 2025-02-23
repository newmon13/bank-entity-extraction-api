package dev.jlipka.bankentityextractionapi.upload.impl.persister.impl;

import dev.jlipka.bankentityextractionapi.bank.api.BankRepository;
import dev.jlipka.bankentityextractionapi.bank.api.model.Bank;
import dev.jlipka.bankentityextractionapi.upload.impl.persister.api.Persistable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankPersisterImpl implements Persistable<Bank> {
    private final BankRepository bankRepository;

    public BankPersisterImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> persist(List<Bank> entities) {
        return bankRepository.saveAll(entities);
    }
}
