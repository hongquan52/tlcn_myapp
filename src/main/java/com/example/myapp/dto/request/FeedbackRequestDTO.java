package com.example.myapp.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDTO {
    private Long id;
    @NotNull(message = "Feedback content is required")
    private String content;
    private double vote;
    private Long user;
    private Long product;

}
