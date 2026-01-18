package com.taskFlow.model;

import com.taskFlow.constants.DBCollections;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = DBCollections.COMPANY)
public class Company {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private Boolean isActive;
    private Boolean isRemoved;
    private String createdBy;
    private Long createdOn;
    private String lastUpdatedBy;
    private Long lastUpdatedOn;

}
