package dev.jlipka.bankentityextractionapi.upload.impl.validator.impl;

import dev.jlipka.bankentityextractionapi.bank.api.model.Bank;
import dev.jlipka.bankentityextractionapi.shared.validator.BankValidator;
import dev.jlipka.bankentityextractionapi.upload.impl.validator.api.Validatable;
import dev.jlipka.bankentityextractionapi.upload.impl.validator.api.ValidationError;
import dev.jlipka.bankentityextractionapi.upload.impl.validator.api.ValidationResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankValidationService implements Validatable<Bank> {
    private final BankValidator bankValidator;

    public BankValidationService(BankValidator bankValidator) {
        this.bankValidator = bankValidator;
    }

    @Override
    public ValidationResult<Bank> validate(List<Bank> banks) {
        List<Bank> validBanks = new ArrayList<>();
        List<ValidationError<Bank>> failures = new ArrayList<>();

        for (Bank bank : banks) {
            BeanPropertyBindingResult errors = new BeanPropertyBindingResult(bank, "bank");
            bankValidator.validate(bank, errors);

            if (!errors.hasErrors()) {
                validBanks.add(bank);
            } else {
                failures.add(new ValidationError<>(
                    bank,
                    errors.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList()
                ));
            }
        }

        return new ValidationResult<>(validBanks, failures);
    }
}