package crm.example.study.model.workplaces.dto;

import java.util.List;

import lombok.Data;

@Data
public class WorkplaceDesignerDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> equipments;
}
