package com.DB.Project.Entitiy;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Doc {
    private int docId;
    private String writer;
    private Date startDate;
    private Date modDate;
    private String content;
    private String header;
    private int userId;
}
