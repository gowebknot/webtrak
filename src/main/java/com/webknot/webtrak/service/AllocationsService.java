package com.webknot.webtrak.service;

import com.webknot.webtrak.dto.AddAllocationDTO;
import com.webknot.webtrak.dto.UpdateAllocationDTO;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.repository.AllocationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateService class.
 * It will have business logic.
 */
@Service
@Slf4j
public class AllocationsService {

    private final AllocationsRepository allocationsRepository;

    private final ProjectService projectService;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public AllocationsService(AllocationsRepository allocationsRepository, ProjectService projectService) {
        this.allocationsRepository = allocationsRepository;
        this.projectService = projectService;
    }

    public Allocation addAllocation(AddAllocationDTO addAllocationDTO, User user, User manager) throws ParseException {

        Project project = projectService.getProjectByCode(addAllocationDTO.getProjectCode());

        if (project == null) {
            throw new BadRequestException("Project does not exist");
        }

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                project.getId());

        if (extAllocation != null) {
            throw new BadRequestException("Allocation already exists, update if required");
        }

        if(!project.getManager().getEmail().equalsIgnoreCase(manager.getEmail())) {
            throw new BadRequestException("Needs to be allotted by a manager");
        }

        Date startDate = addAllocationDTO.getStartDate() == null
                ? new Date() : sdf.parse(addAllocationDTO.getStartDate());

        Allocation allocation = new Allocation();
        allocation.setUser(user);
        allocation.setProject(project);
        allocation.setAllocatedHours(addAllocationDTO.getAllocatedHours());
        allocation.setRole(addAllocationDTO.getRole());
        allocation.setStartDate(new java.sql.Date(startDate.getTime()));

        return allocationsRepository.save(allocation);

    }

    public Allocation updateAllocation(UpdateAllocationDTO updateAllocationDTO, User user, User manager) throws ParseException {

        Project project = projectService.getProjectByCode(updateAllocationDTO.getProjectCode());
        if (project == null) {
            throw new BadRequestException("Project does not exist, create one if required");
        }

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                project.getId());

        if (extAllocation == null) {
            throw new BadRequestException("Allocation does not exist, create one if required");
        }

        if(!project.getManager().getEmail().equalsIgnoreCase(manager.getEmail())) {
            throw new BadRequestException("Needs to be allotted by a manager");
        }


        if (updateAllocationDTO.getStartDate() != null) {
            Date startDate = sdf.parse(updateAllocationDTO.getStartDate());
            extAllocation.setStartDate(new java.sql.Date(startDate.getTime()));
        }

        if (!updateAllocationDTO.isActive()) {
            Date today = new Date();
            extAllocation.setIsActive(false);
            extAllocation.setEndDate(new java.sql.Date(today.getTime()));
        } else {
            extAllocation.setAllocatedHours(updateAllocationDTO.getAllocatedHours());
            extAllocation.setRole(updateAllocationDTO.getRole());
        }

        return allocationsRepository.save(extAllocation);

    }

    public Allocation getAllocations(String projectCode, User user) {
        Project project = projectService.getProjectByCode(projectCode);

        if (project == null) {
            throw new BadRequestException("Project does not exist");
        }
        return allocationsRepository.findByUserIdAndProjectId(user.getId(),
                project.getId());
    }

    public List<Allocation> getAllocations(String projectCode) {
        Project project = projectService.getProjectByCode(projectCode);

        if (project == null) {
            throw new BadRequestException("Project does not exist");
        }
        return allocationsRepository.findByProject(project);
    }

    public List<Allocation> getAllocations(User user) {
        return allocationsRepository.findByUser(user);
    }
}
