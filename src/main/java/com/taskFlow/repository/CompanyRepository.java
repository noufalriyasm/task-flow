package com.taskFlow.repository;

import com.mongodb.MongoException;
import com.taskFlow.constants.DBFields;
import com.taskFlow.model.Company;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CompanyRepository {
  private final MongoTemplate mongoTemplate;

  public Company findCompanyById(final String companyId) throws MongoException {
    Query query = new Query();
    Criteria criteria =
        Criteria.where(DBFields.Id).is(companyId).and(DBFields.IS_REMOVED).is(Boolean.FALSE);
    query.addCriteria(criteria);
    return mongoTemplate.findOne(query, Company.class);
  }

  public Company saveCompany(final Company company) {
    return mongoTemplate.save(company);
  }

  public List<Company> getCompanyList(String createdById) {
    Query query = new Query();
    query.addCriteria(
        Criteria.where(DBFields.IS_REMOVED)
            .is(Boolean.FALSE)
            .and(DBFields.CREATED_BY)
            .is(new ObjectId(createdById)));
    return mongoTemplate.find(query, Company.class);
  }

  public Company getCompanyById(final String companyId) {
    Query query = new Query();
    query.addCriteria(
        Criteria.where(DBFields.Id)
            .is(new ObjectId(companyId))
            .and(DBFields.IS_REMOVED)
            .is(Boolean.FALSE));

    return mongoTemplate.findOne(query,Company.class);
  }
}
