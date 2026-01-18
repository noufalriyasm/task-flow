package com.taskFlow.model;

import com.taskFlow.constants.DBCollections;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = DBCollections.PRIVILEGES)
public class Privilege {
    private String id;
    @Indexed(unique = true)
    private String name;
    private Boolean isRemoved;
}
