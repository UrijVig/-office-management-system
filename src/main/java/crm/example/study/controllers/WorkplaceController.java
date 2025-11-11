package crm.example.study.controllers;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.workplaces.dto.WorkplaceDTO;
import crm.example.study.model.workplaces.dto.WorkplaceDesignerDTO;
import crm.example.study.services.EquipmentService;
import crm.example.study.services.WorkplaceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/workplaces")
@Slf4j
@EnableMethodSecurity(prePostEnabled = true)
public class WorkplaceController {
    
    private WorkplaceService workplaceService;
    private EquipmentService equipmentService;

    public WorkplaceController(WorkplaceService workplaceService, EquipmentService equipmentService) {
        this.workplaceService = workplaceService;
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public String showWorkplacesList(Model model){
        model.addAttribute("workplaces", workplaceService.getAllWorkplaces());
        return "workplaces/workplaces_list";
    }

    @GetMapping("/{id}")
    public String showWorkplacCard(@PathVariable Long id, Model model){
        model.addAttribute("workplace", workplaceService.getWorkplaceById(id));
        return "workplaces/workplace_card";
    }

    @GetMapping("/create")
    public String getWorkplaceCreateForm(Model model){
        model.addAttribute("workplace", new WorkplaceDTO());
        return "workplaces/workplace_create_form";
    }

    @PostMapping("/create")
    public String saveWorkplaceFromCreateForm(@Valid @ModelAttribute("workplace") WorkplaceDTO dto, 
        BindingResult br, Model model){
        if (br.hasErrors()) {
            model.addAttribute("workplace", dto);
            return "workplaces/workplace_create_form";
        }
        try {
            workplaceService.saveWorkplace(dto);
        } catch (Exception e) {
            br.rejectValue("name", "errors.workplace", e.getMessage());
            model.addAttribute("workplace", dto);
            return "workplaces/workplace_create_form";
        }
        return "redirect:/workplaces";
    }

    @GetMapping("/designer")
    public String getWorkplaceDesignerForm(Model model){
        model.addAttribute("workplace", new WorkplaceDesignerDTO());
        model.addAttribute("equipments", equipmentService.getAllEquipments());
        return "workplaces/worcplace_designer_form";
    }

    @PostMapping("/designer")
    public String saveWorkPlaceFromDesignerForm(@Valid @ModelAttribute("workplace") WorkplaceDesignerDTO designerDTO,
        BindingResult br, Model model){
        if (br.hasErrors()) {
            model.addAttribute("workplace", designerDTO);
            model.addAttribute("equipments", equipmentService.getAllEquipments());
            return "workplaces/worcplace_designer_form";
        }
        try {
            workplaceService.saveWorkplaceFromDesignerForm(designerDTO);
        } catch (Exception e) {
            br.rejectValue("name", "errors.workplace", e.getMessage());
            model.addAttribute("workplace", designerDTO);
            model.addAttribute("equipments", equipmentService.getAllEquipments());
            return "workplaces/worcplace_designer_form";
        }
        return "redirect:/workplaces";
    }

    @GetMapping("/descriptionupdate/{id}")
    public String getWorkplaceDescriptionUpdateForm(@PathVariable Long id, Model model){
        model.addAttribute("workplace", workplaceService.getWorkplaceDTOById(id));
        return "workplaces/workplace_description_update_form";
    }

    @PostMapping("/descriptionupdate")
    public String updateWorkplaceDescription(@Valid @ModelAttribute("workplace") WorkplaceDTO dto, 
        BindingResult br, Model model){
        if (br.hasErrors()) {
            model.addAttribute("workplace", dto);
            return "workplaces/workplace_description_update_form";
        }
        workplaceService.updateWorkplaceDescription(dto);
        return "redirect:/workplaces";
    }

    @GetMapping("/equipmentsupdate/{id}")
    public String getWorkplaceEquipmentsUpdateForm(@PathVariable Long id, Model model){
        model.addAttribute("workplace", workplaceService.getWorkplaceDesignerDTOById(id));
        model.addAttribute("equipments", equipmentService.getAllEquipments());
        return "workplaces/workplace_equipments_update_form";
    }


    @PostMapping("/descriptionupdate")
    public String updateWorkplaceEquipments(@Valid @ModelAttribute("workplace") WorkplaceDesignerDTO designerDTO, 
        BindingResult br, Model model){
        if (br.hasErrors()) {
            model.addAttribute("workplace", designerDTO);
            model.addAttribute("equipments", equipmentService.getAllEquipments());
            return "workplaces/workplace_equipments_update_form";
        }
        workplaceService.updateWorkplaceEquipments(designerDTO);
        return "redirect:/workplaces";
    }

    @PostMapping("/delete/{id}")
    public void deleteorkPlaceById(@PathVariable Long id){
        workplaceService.deleteWorkplaceById(id);
    }

}
