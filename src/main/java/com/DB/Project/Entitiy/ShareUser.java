package com.DB.Project.Entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareUser {
    Integer shareUserPK;
    Integer shareUserId;
    String shareId;
    String shareName;
    Integer docID;
    String role;
}
