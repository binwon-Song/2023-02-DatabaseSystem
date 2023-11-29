package com.DB.Project.Entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocWithTodo {
//    private int docId;
//    private String writer;
//    private String modDate;
//    private String content;
//    private String header;
//    private int userId;
    private Doc doc;
    private List<Todo> todos; // 추가: Todo 객체의 리스트
}
