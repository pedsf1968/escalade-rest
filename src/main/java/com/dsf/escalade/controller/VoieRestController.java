package com.dsf.escalade.controller;

import com.dsf.escalade.service.business.VoieService;
import com.dsf.escalade.web.dto.VoieDto;
import com.dsf.escalade.web.dto.VoieFullDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class VoieRestController {
   private final VoieService voieService;

   @Autowired
   public VoieRestController(VoieService voieService) {
      this.voieService = voieService;
   }


   @GetMapping("/voie/{voieId}")
   public ResponseEntity<VoieDto> getVoie(@PathVariable("voieId") Integer voieId) {

      try (VoieDto voieDto = voieService.getOne(voieId)) {
         log.info("GET:/voie/" + voieId);
         return ResponseEntity.ok(voieDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }

   @GetMapping("/voie/full/{voieId}")
   public ResponseEntity<VoieFullDto> getVoieFull(@PathVariable("voieId") Integer voieId) {
      try (VoieFullDto voieFullDto = voieService.getFull(voieId)) {
         log.info("GET:/voie/full/" + voieId);
         return ResponseEntity.ok(voieFullDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }

   @PostMapping(value="/voie", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<VoieDto> updateVoie(@RequestBody VoieDto voieDto) {

      if(voieDto!=null) {
         if(voieDto.getId()!=null) {
            VoieDto oldVoieDto = voieService.getOne(voieDto.getId());
            // verify that the user is the manager of the voie
            if(Boolean.FALSE.equals(voieService.hasRight(oldVoieDto))){
               log.error("UPDATE:/voie ERROR : no rights");
               return ResponseEntity.badRequest().build();
            }
         }
         log.info("UPDATE:/voie" + voieDto);
         Integer voieId = voieService.save(voieDto);
         voieDto = voieService.getOne(voieId);
         return ResponseEntity.ok(voieDto);
      }

      return ResponseEntity.notFound().build();
   }

   @PostMapping(value="/voie/full", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<VoieFullDto> updateVoieFull(@RequestBody VoieFullDto voieFullDto)  {

      if(voieFullDto!=null) {
         if(voieFullDto.getVoie().getId()!=null) {
            VoieDto oldVoieDto = voieService.getOne(voieFullDto.getVoie().getId());
            // verify that the user is the manager of the voie
            if(Boolean.FALSE.equals(voieService.hasRight(oldVoieDto))){
               log.error("UPDATE:/voie/full ERROR : no rights");
               return ResponseEntity.badRequest().build();
            }
         }
         log.info("UPDATE:/voie/full" + voieFullDto);
         Integer voieId = voieService.saveFull(voieFullDto);
         voieFullDto = voieService.getFull(voieId);
         return ResponseEntity.ok(voieFullDto);
      }

      return ResponseEntity.notFound().build();
   }

}
