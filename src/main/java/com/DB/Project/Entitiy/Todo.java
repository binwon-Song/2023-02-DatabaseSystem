package com.DB.Project.Entitiy;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private int TodoID;
    private String header;
    private int priority;
    private int DocID;

}
