package com.challenge.dao.impl;

import com.challenge.dao.AbstractDao;
import com.challenge.dao.TransactionDao;
import com.challenge.model.Transaction;
import com.google.common.base.Preconditions;
import lombok.NonNull;

import javax.naming.OperationNotSupportedException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao {

}
