package com.taskFlow.repository;

import com.mongodb.MongoException;
import com.taskFlow.constants.DBFields;
import com.taskFlow.dto.SignUpDto;
import com.taskFlow.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;

@Data
@Repository
public class UserRepository {
   private final MongoTemplate mongoTemplate;

  public User findUserByLoginId(String loginId) throws MongoException {
    Query query = new Query();
    query
        .addCriteria(Criteria.where(DBFields.LOGIN_ID).is(loginId))
        .addCriteria(Criteria.where(DBFields.IS_REMOVED).is(Boolean.FALSE));
    return mongoTemplate.findOne(query, User.class);
  }

  public User saveUser(final  User user){
    return mongoTemplate.save(user);

  }
}
