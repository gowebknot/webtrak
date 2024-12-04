package com.webknot.webtrak.service;

import com.webknot.webtrak.dto.AddAllocationDTO;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.repository.AllocationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

  public AllocationsService(AllocationsRepository allocationsRepository) {
    this.allocationsRepository = allocationsRepository;
  }

  public void addAllocation(AddAllocationDTO addAllocationDTO, User user) {

      List<Allocation> allocations = allocationsRepository.findByUser(user.getId(), );

  }

}
