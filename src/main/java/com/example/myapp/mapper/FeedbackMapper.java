package com.example.myapp.mapper;

import com.example.myapp.dto.request.FeedbackRequestDTO;
import com.example.myapp.dto.response.FeedbackResponseDTO;
import com.example.myapp.entites.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "vote", source = "dto.vote")
    @Mapping(target = "user", expression = "java(null)")
    @Mapping(target = "product", expression = "java(null)")
    @Mapping(target = "createDate", expression = "java(null)")
    @Mapping(target = "updateDate", expression = "java(null)")
    Feedback feedbackRequestDTOToFeedback(FeedbackRequestDTO dto);


    @Mapping(target = "id", source = "f.id")
    @Mapping(target = "content", source = "f.content")
    @Mapping(target = "vote", source = "f.vote")
    @Mapping(target = "user", source = "f.user.id")
    @Mapping(target = "userName", source = "f.user.name")
    @Mapping(target = "product", source = "f.product.id")
    @Mapping(target = "productName", source = "f.product.name")
    @Mapping(target = "productThumbnail", source = "f.product.image")
    @Mapping(target = "createDate", source = "f.createDate")
    @Mapping(target = "updateDate", source = "f.updateDate")

    FeedbackResponseDTO feedbackToFeedbackResponseDTO(Feedback f);
}
