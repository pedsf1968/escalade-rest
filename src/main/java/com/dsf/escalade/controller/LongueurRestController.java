package com.dsf.escalade.controller;

import com.dsf.escalade.service.business.LongueurService;
import com.dsf.escalade.service.business.VoieService;
import com.dsf.escalade.web.dto.LongueurDto;
import com.dsf.escalade.web.dto.LongueurFullDto;
import com.dsf.escalade.web.dto.VoieDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LongueurRestController {
   private final VoieService voieService;
   private final LongueurService longueurService;

   @Autowired
   public LongueurRestController(VoieService voieService, LongueurService longueurService) {
      this.voieService = voieService;
      this.longueurService = longueurService;
   }

   @GetMapping("/longueur/{longueurId}")
   public ResponseEntity<LongueurDto> getLongueur(@PathVariable("longueurId") Integer longueurId) {

      try (LongueurDto longueurDto = longueurService.getOne(longueurId)) {
         log.info("GET:/longueur/" + longueurId);
         return ResponseEntity.ok(longueurDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }

   @GetMapping("/longueur/full/{longueurId}")
   public ResponseEntity<LongueurFullDto> getLongueurFull(@PathVariable("longueurId") Integer longueurId) {

      try (LongueurFullDto longueurFullDto = longueurService.getFull(longueurId)) {
         log.info("GET:/longueur/full/" + longueurId);
         return ResponseEntity.ok(longueurFullDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }

   @PostMapping(value="/longueur", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<LongueurDto> updateLongueur(@RequestBody LongueurDto longueurDto) {

      if(longueurDto!=null) {
         if(longueurDto.getVoieId()!=null) {
            VoieDto oldVoieDto = voieService.getOne(longueurDto.getVoieId());
            // verify that the user is the manager of the voie
            if(Boolean.FALSE.equals(voieService.hasRight(oldVoieDto))){
               log.error("UPDATE:/longueur ERROR : no rights");
               return ResponseEntity.badRequest().build();
            }
         }
         log.info("UPDATE:/longueur" + longueurDto);
         Integer longueurId = longueurService.save(longueurDto);
         longueurDto = longueurService.getOne(longueurId);
         return ResponseEntity.ok(longueurDto);
      }

      return ResponseEntity.notFound().build();
   }

   @PostMapping(value="/longueur/full", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<LongueurFullDto> updateLongueurFull(@RequestBody LongueurFullDto longueurFullDto) {

      if(longueurFullDto!=null) {
         if(longueurFullDto.getLongueur().getVoieId()!=null) {
            VoieDto oldVoieDto = voieService.getOne(longueurFullDto.getLongueur().getVoieId());
            // verify that the user is the manager of the voie
            if(Boolean.FALSE.equals(voieService.hasRight(oldVoieDto))){
               log.error("UPDATE:/longueur/full ERROR : no rights");
               return ResponseEntity.badRequest().build();
            }
         }
         log.info("UPDATE:/longueur/full" + longueurFullDto);
         Integer longueurId = longueurService.saveFull(longueurFullDto);
         longueurFullDto = longueurService.getFull(longueurId);
         return ResponseEntity.ok(longueurFullDto);
      }

      return ResponseEntity.notFound().build();
   }

}
