package crm.example.study.controllers.mvc;

import java.util.List;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.equipment.Equipment;
import crm.example.study.model.workplaces.dto.WorkplaceDTO;
import crm.example.study.model.workplaces.dto.WorkplaceDesignerDTO;
import crm.example.study.services.EquipmentService;
import crm.example.study.services.WorkplaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/workplaces")
@Slf4j
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WorkplaceController {
    
    private final WorkplaceService workplaceService;
    private final EquipmentService equipmentService;

    @GetMapping
    public String showWorkplacesList(Model model){
        log.debug("Запрос на отображение списка рабочих мест. ");
        model.addAttribute("workplaces", workplaceService.getAllWorkplaces());
        return "workplaces/workplaces_list";
    }

    @GetMapping("/{id}")
    public String showWorkplacCard(@PathVariable Long id, Model model){
        log.debug("Запрос на отображение карточки рабочего места. ");
        model.addAttribute("workplace", workplaceService.getWorkplaceById(id));
        return "workplaces/workplace_card";
    }

    @GetMapping("/create")
    public String getWorkplaceCreateForm(Model model){
        log.debug("Запрос на создание карточки рабочего места. ");
        model.addAttribute("workplace", new WorkplaceDTO());
        return "workplaces/workplace_create_form";
    }

    @PostMapping("/create")
    public String saveWorkplaceFromCreateForm(@Valid @ModelAttribute("workplace") WorkplaceDTO dto, 
        BindingResult br, Model model){
        if (br.hasErrors()) {
            log.debug("ошибка заолнения формы при создании карточки рабочего места {}", dto.getName());
            model.addAttribute("workplace", dto);
            return "workplaces/workplace_create_form";
        }
        try {
            log.info("Попытка сохранения карточки рабочего места {}", dto.getName());
            workplaceService.saveWorkplace(dto);
        } catch (Exception e) {
            log.error("Ошибка при сохранении рабочего места", e.getMessage());
            br.rejectValue("name", "errors.workplace", e.getMessage());
            model.addAttribute("workplace", dto);
            return "workplaces/workplace_create_form";
        }
        log.info("Рабочее место {} сохранено успешно", dto.getName());
        return "redirect:/workplaces";
    }

    @GetMapping("/designer")
    public String getWorkplaceDesignerForm(Model model){
        log.debug("Запрос на создание карточки рабочего места через конструктор. ");
        model.addAttribute("workplace", new WorkplaceDesignerDTO());
        model.addAttribute("equipments", workplaceService.getWorkplaceByName("STORAGE").getEquipments());
        return "workplaces/workplace_designer_form";
    }

    @PostMapping("/designer")
    public String saveWorkPlaceFromDesignerForm(@Valid @ModelAttribute("workplace") WorkplaceDesignerDTO designerDTO,
        BindingResult br, Model model){
        if (br.hasErrors()) {
            log.debug("ошибка заолнения формы при создании карточки рабочего места {}", designerDTO.getName());
            model.addAttribute("workplace", designerDTO);
            model.addAttribute("equipments", workplaceService.getWorkplaceByName("STORAGE").getEquipments());
            return "workplaces/workplace_designer_form";
        }
        try {
            log.info("Попытка сохранения карточки рабочего места {}", designerDTO.getName());
            workplaceService.saveWorkplaceFromDesignerForm(designerDTO);
        } catch (Exception e) {
            log.error("Ошибка при сохранении рабочего места", e.getMessage());
            br.rejectValue("name", "errors.workplace", e.getMessage());
            model.addAttribute("workplace", designerDTO);
            model.addAttribute("equipments", workplaceService.getWorkplaceByName("STORAGE").getEquipments());
            return "workplaces/workplace_designer_form";
        }
        log.info("Рабочее место {} сохранено успешно", designerDTO.getName());
        return "redirect:/workplaces";
    }

    @GetMapping("/descriptionupdate/{id}")
    public String getWorkplaceDescriptionUpdateForm(@PathVariable Long id, Model model){
        log.debug("Запрос на редактирование описания карточки рабочего места. ");
        model.addAttribute("workplace", workplaceService.getWorkplaceDTOById(id));
        return "workplaces/workplace_description_update_form";
    }

    @PostMapping("/descriptionupdate")
    public String updateWorkplaceDescription(@Valid @ModelAttribute("workplace") WorkplaceDTO dto, 
        BindingResult br, Model model){
        if (br.hasErrors()) {
            log.debug("ошибка заолнения формы при редактировании описания карточки рабочего места {}", dto.getName());
            model.addAttribute("workplace", dto);
            return "workplaces/workplace_description_update_form";
        }
        log.info("Попытка редактирования описания карточки рабочего места {}", dto.getName());
        workplaceService.updateWorkplaceDescription(dto);
        log.info("Описание рабочего места {} сохранено успешно", dto.getName());
        return "redirect:/workplaces";
    }

    @GetMapping("/equipmentsupdate/{id}")
    public String getWorkplaceEquipmentsUpdateForm(@PathVariable Long id, Model model){
        log.debug("Запрос на редактирование оборудования рабочего места. ");
        model.addAttribute("workplace", workplaceService.getWorkplaceDesignerDTOById(id));
        List<Equipment> equipments = workplaceService.getWorkplaceByName("STORAGE").getEquipments();
        equipments.addAll(workplaceService.getWorkplaceByName(workplaceService.getWorkplaceById(id).getName()).getEquipments());
        model.addAttribute("equipments", equipments);
        return "workplaces/workplace_equipments_update_form";
    }


    @PostMapping("/equipmentsupdate")
    public String updateWorkplaceEquipments(@Valid @ModelAttribute("workplace") WorkplaceDesignerDTO designerDTO, 
        BindingResult br, Model model){
        if (br.hasErrors()) {
            log.debug("ошибка заолнения формы при редактировании оборудования рабочего места {}", designerDTO.getName());
            model.addAttribute("workplace", designerDTO);
            List<Equipment> equipments = workplaceService.getWorkplaceByName("STORAGE").getEquipments();
            equipments.addAll(workplaceService.getWorkplaceByName(designerDTO.getName()).getEquipments());
            model.addAttribute("equipments", equipmentService.getAllEquipments());
            return "workplaces/workplace_equipments_update_form";
        }
        log.info("Попытка редактирования оборудования рабочего места {}", designerDTO.getName());
        workplaceService.updateWorkplaceEquipments(designerDTO);
        log.info("Изменение оборудования рабочего места {} сохранено успешно", designerDTO.getName());
        return "redirect:/workplaces";
    }

    @PostMapping("/delete/{id}")
    public String deleteorkPlaceById(@PathVariable Long id){
        log.info("Попытка удаления рабочего места {}", id);
        workplaceService.deleteWorkplaceById(id);
        log.info("Удаление рабочего места {} прошло успешно", id);
        return "redirect:/workplaces";
    }

}
