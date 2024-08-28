package com.cassio.nicepay.repository;

import com.cassio.nicepay.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  boolean existsByDocument(String document);
  boolean existsByEmail(String document);
}
