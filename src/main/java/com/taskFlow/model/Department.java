package com.taskFlow.model;

import com.taskFlow.constants.DBCollections;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = DBCollections.DEPARTMENTS)
public class Department {
    @Id
    private String id;
    private String name;
    private String companyId;
    private Boolean isActive;
    private Boolean isRemoved;
    private String createdBy;
    private Long createdOn;
    private String lastUpdatedBy;
    private Long lastUpdatedOn;
}
