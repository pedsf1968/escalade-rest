package com.dsf.escalade.controller;

import com.dsf.escalade.service.business.LongueurService;
import com.dsf.escalade.web.dto.LongueurFullDto;
import com.dsf.escalade.web.dto.LongueurDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LongueurRestController {
   private final LongueurService longueurService;

   @Autowired
   public LongueurRestController(LongueurService longueurService) {
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
   public ResponseEntity<LongueurDto> updateLongueur(@RequestBody LongueurDto longueurDto) throws Exception {

      if(longueurDto!=null) {
         log.info("UPDATE:/longueur/" + longueurDto);
         Integer longueurId = longueurService.save(longueurDto);
         longueurDto = longueurService.getOne(longueurId);
         return ResponseEntity.ok(longueurDto);
      }

      return ResponseEntity.notFound().build();
   }

   @PostMapping(value="/longueur/full", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<LongueurFullDto> updateLongueurFull(@RequestBody LongueurFullDto longueurFullDto) throws Exception {

      if(longueurFullDto!=null) {
         log.info("UPDATE:/longueur/full/" + longueurFullDto);
         Integer longueurId = longueurService.saveFull(longueurFullDto);
         longueurFullDto = longueurService.getFull(longueurId);
         return ResponseEntity.ok(longueurFullDto);
      }

      return ResponseEntity.notFound().build();
   }

}
