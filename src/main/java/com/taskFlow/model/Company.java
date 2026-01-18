package com.taskFlow.model;

import com.taskFlow.constants.DBCollections;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = DBCollections.COMPANY)
@Builder
public class Company {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String name;
    private Boolean isActive;
    private Boolean isRemoved;
    private ObjectId createdBy;
    private Long createdOn;
    private ObjectId lastUpdatedBy;
    private Long lastUpdatedOn;
}
