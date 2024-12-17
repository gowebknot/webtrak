package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.AddAllocationDTO;
import com.webknot.webtrak.dto.AllocationOutputDTO;
import com.webknot.webtrak.dto.GenericResponseDTO;
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
import java.util.Map;

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
    public ResponseEntity<GenericResponseDTO> addAllocation(@RequestHeader("Authorization") String authorization,
                                                            @RequestBody AddAllocationDTO addAllocationDTO) throws ParseException {


        User manager = userService.getUserByEmail(addAllocationDTO.getManagerEmail());
        if (manager == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("Manager not found", null));
        }


        User user = userService.getUserByEmail(addAllocationDTO.getEmployeeEmail());

        if (user == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("User not found", null));
        }


        try {
            Utils.verifyPassword(authorization, manager.getPrivateKey());
            return ResponseEntity.ok().body(new GenericResponseDTO("success",
                    allocationsService.addAllocation(addAllocationDTO, user, manager)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }

    }

    @GetMapping(value = "allocation")
    public ResponseEntity<GenericResponseDTO> getAllocations(@RequestParam(required = false) String projectCode,
                                  @RequestParam(required = false) String userEmail) {


        if (projectCode != null && userEmail != null) {


            try {
                User user = userService.getUserByEmail(userEmail);
                Allocation allocation = allocationsService.getAllocations(projectCode, user);

                return ResponseEntity.ok().body(new GenericResponseDTO("success",
                        Utils.getAllocationOutputDTO(Arrays.asList(allocation))));
            } catch (BadRequestException ex) {
                return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
            }

        }

        if (projectCode != null) {

            try {

                return ResponseEntity.ok().body(new GenericResponseDTO("success",
                        Utils.getAllocationOutputDTO(allocationsService.getAllocations(projectCode))));
            } catch (BadRequestException ex) {
                return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
            }
        }

        if (userEmail != null) {
            try {
                User user = userService.getUserByEmail(userEmail);
                return ResponseEntity.ok().body(new GenericResponseDTO("success",
                        Utils.getAllocationOutputDTO(allocationsService.getAllocations(user))));
            } catch (BadRequestException ex) {
                return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
            }
        }

        return ResponseEntity.ok().body(new GenericResponseDTO("success", Collections.emptyList()));

    }

    @PutMapping("allocation")
    public ResponseEntity<GenericResponseDTO> updateAllocation(@RequestHeader("Authorization") String authorization,
            @RequestBody UpdateAllocationDTO updateAllocationDTO) throws ParseException {

        User user = userService.getUserByEmail(updateAllocationDTO.getEmployeeEmail());

        User manager = userService.getUserByEmail(updateAllocationDTO.getManagerEmail());
        if (manager == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("Manager not found", null));
        }

        if (user == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("User not found", null));
        }

        Utils.verifyPassword(authorization, manager.getPrivateKey());

        try {
            return ResponseEntity.ok().body(new GenericResponseDTO("success",
                    allocationsService.updateAllocation(updateAllocationDTO, user, manager)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }
    }

}
