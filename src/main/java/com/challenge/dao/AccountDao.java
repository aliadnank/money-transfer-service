package com.challenge.dao;

import com.challenge.model.Account;
import lombok.NonNull;

import java.util.Optional;

public interface AccountDao extends CoreDao<Account> {

    Optional<Account> findByCustomerId(Long customerId);
}
