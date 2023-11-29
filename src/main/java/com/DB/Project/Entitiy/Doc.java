package com.DB.Project.Entitiy;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Doc {
    private int docId;
    private String writer;
    private String modDate;
    private String content;
    private String header;
    private int userId;


}
