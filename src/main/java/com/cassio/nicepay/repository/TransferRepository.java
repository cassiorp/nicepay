package com.cassio.nicepay.repository;

import com.cassio.nicepay.entity.Transfer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransferRepository extends MongoRepository<Transfer, String> {

}
