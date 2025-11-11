package crm.example.study.model.workplaces.dto;

import crm.example.study.model.workplaces.Workplace;
import lombok.Data;

@Data
public class WorkplaceDTO {

    private Long id;
    private String name;
    private String description;

    public WorkplaceDTO(Workplace workplace){
        this.id = workplace.getId();
        this.name = workplace.getName();
        this.description = workplace.getDescription();
    }
}
