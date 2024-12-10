package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.AddAllocationDTO;
import com.webknot.webtrak.dto.AllocationOutputDTO;
import com.webknot.webtrak.dto.UpdateAllocationDTO;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.service.AllocationsService;
import com.webknot.webtrak.service.UserService;
import com.webknot.webtrak.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateServiceController class.
 * It will have all API end points definition.
 */
@RestController
public class AllocationsServiceController extends BaseController {

    private final AllocationsService allocationsService;
    private final UserService userService;

    public AllocationsServiceController(AllocationsService allocationsService, UserService userService) {
        this.allocationsService = allocationsService;
        this.userService = userService;
    }


    @PostMapping("allocation")
    public Allocation addAllocation(@RequestHeader("Authorization") String authorization,
                                    @RequestBody AddAllocationDTO addAllocationDTO) throws ParseException {


        User manager = userService.getUserByEmail(addAllocationDTO.getManagerEmail());
        if (manager == null) {
            throw new BadRequestException("Manager not found");
        }

        Utils.verifyPassword(authorization, manager.getPrivateKey());


        User user = userService.getUserByEmail(addAllocationDTO.getEmployeeEmail());

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        return allocationsService.addAllocation(addAllocationDTO, user, manager);

    }

    @GetMapping(value = "allocation")
    public List<AllocationOutputDTO> getAllocations(@RequestParam(required = false) String projectCode,
                                  @RequestParam(required = false) String userEmail) {


        if (projectCode != null && userEmail != null) {
            User user = userService.getUserByEmail(userEmail);
            Allocation allocation = allocationsService.getAllocations(projectCode, user);

            return Utils.getAllocationOutputDTO(Arrays.asList(allocation));
        }

        if (projectCode != null) {
            return Utils.getAllocationOutputDTO(allocationsService.getAllocations(projectCode));
        }

        if (userEmail != null) {
            User user = userService.getUserByEmail(userEmail);
            return Utils.getAllocationOutputDTO(allocationsService.getAllocations(user));
        }

        return Collections.emptyList();

    }

    @PutMapping("allocation")
    public Allocation updateAllocation(@RequestHeader("Authorization") String authorization,
            @RequestBody UpdateAllocationDTO updateAllocationDTO) throws ParseException {

        User user = userService.getUserByEmail(updateAllocationDTO.getEmployeeEmail());

        User manager = userService.getUserByEmail(updateAllocationDTO.getManagerEmail());
        if (manager == null) {
            throw new BadRequestException("Manager not found");
        }

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        Utils.verifyPassword(authorization, manager.getPrivateKey());

        return allocationsService.updateAllocation(updateAllocationDTO, user, manager);

    }

}
