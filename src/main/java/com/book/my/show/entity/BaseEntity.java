package com.book.my.show.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 5004711179355651018L;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
