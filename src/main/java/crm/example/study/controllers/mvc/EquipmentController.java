package crm.example.study.controllers.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.model.equipment.DTO.EquipmentDTO;
import crm.example.study.services.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/equipments")
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String showAllEquipments(Model model) {
        log.debug("Запрос на отображение списка оборудования. ");
        model.addAttribute("equipments", equipmentService.getAllEquipments());
        return "equipments/equipments_list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createEquipmentCard(Model model) {
        log.debug("Запрос на создание карточки оборудования. ");
        model.addAttribute("equipment", new EquipmentDTO());
        model.addAttribute("types", equipmentService.getAllTypes());
        model.addAttribute("statuses", equipmentService.getAllStatuses());
        model.addAttribute("locations", equipmentService.getAllLocations());
        return "equipments/equipment_create_form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String saveEquipmentCard(@Valid @ModelAttribute("equipment") EquipmentDTO dto,
            BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("equipment", dto);
            model.addAttribute("types", equipmentService.getAllTypes());
            model.addAttribute("statuses", equipmentService.getAllStatuses());
            model.addAttribute("locations", equipmentService.getAllLocations());
            log.debug("ошибка заолнения формы при создании карточки оборудования {}", dto.getSerialNumber());
            return "equipments/equipment_create_form";
        }
        try {
            log.info("Попытка сохранения карточки оборудования {}", dto.getSerialNumber());
            equipmentService.saveEquipment(dto);
        } catch (Exception e) {
            log.error("Ошибка при сохранении карточки оборудования", e.getMessage());
            br.rejectValue("serialNumber", "error.equipment", e.getMessage());
            model.addAttribute("equipment", dto);
            model.addAttribute("types", equipmentService.getAllTypes());
            model.addAttribute("statuses", equipmentService.getAllStatuses());
            model.addAttribute("locations", equipmentService.getAllLocations());
            return "equipments/equipment_create_form";
        }
        log.info("Оборудование {} сохранено успешно", dto.getSerialNumber());
        return "redirect:/equipments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{id}")
    public String getUpdateEquipmentForm(@PathVariable Long id, Model model) {
        log.debug("Запрос на редактирование карточки оборудования {}", id);
        model.addAttribute("equipment", equipmentService.getEquipmentDTOById(id));
        return "equipments/equipment_update_form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String updateEquipmentCard(@Valid @ModelAttribute("equipment") EquipmentDTO dto,
            BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("equipment", dto);
            log.debug("ошибка заолнения формы при редактировании карточки оборудования {}", dto.getSerialNumber());
            return "equipments/equipment_update_form";
        }
        log.info("Попытка сохранения изменений в карточке оборудования {}", dto.getSerialNumber());
        equipmentService.updateEquipment(dto);
        log.info("Информация об оборудовании {} обновлена успешно", dto.getSerialNumber());
        return "redirect:/equipments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable Long id) {
        log.info("Попытака удалёния карточки оборудования: {}", id);
        equipmentService.deleteEquipment(id);
        log.info("Карточка оборудования {} удалена.", id);
        return "redirect:/equipments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public String getEquipmentCard(@PathVariable Long id, Model model) {
        log.info("Запрос на просмотр карточки оборудования: {}", id);
        model.addAttribute("equipment", equipmentService.getEquipmentById(id));
        return "equipments/equipment_card";
    }

}
