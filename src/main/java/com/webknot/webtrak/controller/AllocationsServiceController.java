package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.AddAllocationDTO;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.service.AllocationsService;
import com.webknot.webtrak.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> addAllocation(@RequestBody AddAllocationDTO addAllocationDTO) {

        User user = userService.getUserByEmail(addAllocationDTO.getEmployeeEmail());

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        allocationsService.addAllocation(addAllocationDTO, user);
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @GetMapping(value = "allocation")
    public List<Allocation> getAllocations(@RequestParam(required = false) Long projectId,
                                  @RequestParam(required = false) String userEmail) {


        if (projectId != null && userEmail != null) {
            User user = userService.getUserByEmail(userEmail);
            Allocation allocation = allocationsService.getAllocations(projectId, user);
            return Arrays.asList(allocation);
        }

        if (projectId != null) {
            return allocationsService.getAllocations(projectId);
        }

        if (userEmail != null) {
            User user = userService.getUserByEmail(userEmail);
            return allocationsService.getAllocations(user);
        }

        return Collections.emptyList();

    }

    @PutMapping("allocation")
    public ResponseEntity<?> updateAllocation(@RequestBody AddAllocationDTO addAllocationDTO) {

        User user = userService.getUserByEmail(addAllocationDTO.getEmployeeEmail());

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        allocationsService.updateAllocation(addAllocationDTO, user);
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @DeleteMapping("allocation")
    public ResponseEntity<?> deleteAllocation(@RequestBody AddAllocationDTO addAllocationDTO) {

        User user = userService.getUserByEmail(addAllocationDTO.getEmployeeEmail());

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        allocationsService.deleteAllocation(addAllocationDTO, user);
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

}
