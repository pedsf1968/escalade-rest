package com.dsf.escalade.controller;

import com.dsf.escalade.service.business.TopoService;
import com.dsf.escalade.web.dto.TopoDto;
import com.dsf.escalade.web.dto.TopoFullDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class TopoRestController {
   private final TopoService topoService;

   @Autowired
   public TopoRestController(TopoService topoService) {
      this.topoService = topoService;
   }

   @GetMapping("/topo/{topoId}")
   public ResponseEntity<TopoDto> getTopo(@PathVariable("topoId") Integer topoId) {

      try(TopoDto topoDto = topoService.getOne(topoId)) {
         log.info("GET:/topo/" + topoId);
         return ResponseEntity.ok(topoDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }


   @GetMapping("/topo/full/{topoId}")
   public ResponseEntity<TopoFullDto> getTopoFull(@PathVariable("topoId") Integer topoId) {

      try(TopoFullDto topofullDto = topoService.getFull(topoId)) {
         log.info("GET:/topo/full/" + topoId);
         return ResponseEntity.ok(topofullDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }

   @PostMapping(value="/topo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<TopoDto> updateTopo(@RequestBody TopoDto topoDto) {

      if(topoDto!=null) {
         if(topoDto.getId()!=null) {
            TopoDto oldTopoDto = topoService.getOne(topoDto.getId());
            // verify that the user is the manager of the topo
            if(Boolean.FALSE.equals(topoService.hasRight(oldTopoDto))){
               log.error("UPDATE:/topo ERROR : no rights");
               return ResponseEntity.badRequest().build();
            }
         }
         log.info("UPDATE:/topo" + topoDto);
         Integer sectorId = topoService.save(topoDto);
         topoDto = topoService.getOne(sectorId);
         return ResponseEntity.ok(topoDto);
      }

      return ResponseEntity.notFound().build();
   }

   @PostMapping(value="/topo/full", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<TopoFullDto> updateTopoFull(@RequestBody TopoFullDto topoFullDto) {

      if(topoFullDto!=null) {
         if(topoFullDto.getTopo().getId()!=null) {
            TopoDto oldTopoDto = topoService.getOne(topoFullDto.getTopo().getId());
            // verify that the user is the manager of the topo
            if(Boolean.FALSE.equals(topoService.hasRight(oldTopoDto))){
               log.error("UPDATE:/topo/full ERROR : no rights");
               return ResponseEntity.badRequest().build();
            }
         }
         log.info("UPDATE:/topo/full" + topoFullDto);
         Integer sectorId = topoService.saveFull(topoFullDto);
         topoFullDto = topoService.getFull(sectorId);
         return ResponseEntity.ok(topoFullDto);
      }

      return ResponseEntity.notFound().build();
   }


}
