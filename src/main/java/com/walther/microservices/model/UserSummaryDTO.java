package com.walther.microservices.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDTO extends RepresentationModel<UserSummaryDTO> {
    private Integer id;
    private String name;
    private String lastName;
}
