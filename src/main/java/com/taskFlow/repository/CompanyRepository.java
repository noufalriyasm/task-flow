package com.taskFlow.repository;

import com.mongodb.MongoException;
import com.taskFlow.constants.DBFields;
import com.taskFlow.model.Company;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CompanyRepository {
  private final MongoTemplate mongoTemplate;

  public Company findCompanyById(final String companyId) throws MongoException {
    Query query = new Query();
    Criteria criteria =
        Criteria.where(DBFields.Id).is(companyId).and(DBFields.IS_REMOVED).is(Boolean.FALSE);
    query.addCriteria(criteria);
    return mongoTemplate.findOne(query,Company.class);
  }
}
