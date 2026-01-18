package com.taskFlow.repository;

import com.mongodb.MongoException;
import com.taskFlow.constants.DBFields;
import com.taskFlow.model.Department;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DepartmentRepository {
  private final MongoTemplate mongoTemplate;

  public Department findDepartmentById(final String companyId, final String departmentId) throws MongoException {
    Query query = new Query();
    Criteria criteria =
        Criteria.where(DBFields.COMPANY_ID)
            .is(companyId)
            .and(DBFields.IS_REMOVED)
            .is(Boolean.FALSE)
            .and(DBFields.Id)
            .is(departmentId);
    query.addCriteria(criteria);
    return mongoTemplate.findOne(query, Department.class);
  }
}
