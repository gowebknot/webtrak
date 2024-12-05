package com.webknot.webtrak.service;

import com.webknot.webtrak.dto.AddAllocationDTO;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.repository.AllocationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public AllocationsService(AllocationsRepository allocationsRepository, ProjectService projectService) {
        this.allocationsRepository = allocationsRepository;
        this.projectService = projectService;
    }

    public void addAllocation(AddAllocationDTO addAllocationDTO, User user) {

        Optional<Project> project = projectService.getProjectById(addAllocationDTO.getProjectId());

        if (project.isEmpty()) {
            throw new BadRequestException("Project does not exist");
        }

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                addAllocationDTO.getProjectId());

        if (extAllocation != null) {
            throw new BadRequestException("Allocation already exists, update if required");
        }


        Allocation allocation = new Allocation();
        allocation.setUser(user);
        allocation.setProject(project.get());
        allocation.setAllocatedHours(addAllocationDTO.getAllocatedHours());
        allocation.setRole(addAllocationDTO.getRole());

        allocationsRepository.save(allocation);

    }

    public void updateAllocation(AddAllocationDTO addAllocationDTO, User user) {

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                addAllocationDTO.getProjectId());

        if (extAllocation == null) {
            throw new BadRequestException("Allocation does not exist, create one if required");
        }

        extAllocation.setAllocatedHours(addAllocationDTO.getAllocatedHours());
        extAllocation.setRole(addAllocationDTO.getRole());

        allocationsRepository.save(extAllocation);

    }

    public void deleteAllocation(AddAllocationDTO addAllocationDTO, User user) {

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                addAllocationDTO.getProjectId());

        if (extAllocation == null) {
            throw new BadRequestException("Allocation does not exist");
        }

        allocationsRepository.delete(extAllocation);

    }

    public Allocation getAllocations(Long projectId, User user) {
        return allocationsRepository.findByUserIdAndProjectId(user.getId(),
                projectId);
    }

    public List<Allocation> getAllocations(Long projectId) {
        Optional<Project> project = projectService.getProjectById(projectId);

        if (project.isEmpty()) {
            throw new BadRequestException("Project does not exist");
        }
        return allocationsRepository.findByProject(project.get());
    }

    public List<Allocation> getAllocations(User user) {
        return allocationsRepository.findByUser(user);
    }
}
