package software.sigma.sip.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import software.sigma.sip.domain.entity.Test;

@Data
@Builder
public class TestDto {
    Long id;
    String testField;

    public static TestDto toTestDto(Test test) {
        return TestDto.builder()
                .id(test.getId())
                .testField(test.getTestField())
                .build();
    }
}
