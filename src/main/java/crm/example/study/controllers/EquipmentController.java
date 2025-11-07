package crm.example.study.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import crm.example.study.services.EquipmentService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/equipments")
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
public class EquipmentController {

    private EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String showAllEquipments(Model model){
        log.debug("Запрос на отображение списка оборудования. ");
        model.addAttribute("equipments", equipmentService.getAll());
        return "equipments/equipments_list";
    }

}
